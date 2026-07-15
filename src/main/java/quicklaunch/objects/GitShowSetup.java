package quicklaunch.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class GitShowSetup {

    public interface StatusListener {
        void onStatus(String message);
    }

    public static class GitSetupException extends Exception {
        private static final long serialVersionUID = 1L;

        public GitSetupException(String message) {
            super(message);
        }
    }

    private static class GitResult {
        int exitCode;
        String output;
    }

    private final File repoDir;
    private final String stagingShowRoot;

    public GitShowSetup(String repoPath, String stagingShowRoot) {
        this.repoDir = new File(repoPath);
        this.stagingShowRoot = stagingShowRoot;
    }

    public static boolean isValidShowId(String showId) {
        return showId != null && showId.matches("[A-Za-z0-9_-]+");
    }

    public String setUpShow(String showId, StatusListener listener) throws GitSetupException {
        if (!isValidShowId(showId)) {
            throw new GitSetupException("Invalid show id '" + showId
                    + "' — letters, numbers, dash and underscore only.");
        }
        if (!new File(this.repoDir, ".git").isDirectory()) {
            throw new GitSetupException("MYS-Shows repo not found at " + this.repoDir.getPath());
        }

        String branchName = showId;

        listener.onStatus("Checking working tree...");
        GitResult status = runGit("status", "--porcelain");
        if (status.exitCode != 0) {
            throw new GitSetupException("git status failed:\n" + status.output);
        }
        if (status.output.length() > 0) {
            throw new GitSetupException("MYS-Shows working tree has uncommitted changes — "
                    + "commit or stash them first, then re-run git:" + showId
                    + ". Nothing was changed.");
        }

        listener.onStatus("Fetching origin...");
        GitResult fetch = runGit("fetch", "origin");
        if (fetch.exitCode != 0) {
            throw new GitSetupException("git fetch origin failed:\n" + fetch.output);
        }

        boolean localExists = runGit("rev-parse", "--verify", "--quiet",
                "refs/heads/" + branchName).exitCode == 0;
        boolean remoteExists = runGit("rev-parse", "--verify", "--quiet",
                "refs/remotes/origin/" + branchName).exitCode == 0;

        String branchSummary;
        if (localExists || remoteExists) {
            listener.onStatus("Checking out " + branchName + "...");
            GitResult checkout = runGit("checkout", branchName);
            if (checkout.exitCode != 0) {
                throw new GitSetupException("git checkout " + branchName + " failed:\n"
                        + checkout.output);
            }
            if (remoteExists) {
                listener.onStatus("Pulling " + branchName + " from origin...");
                GitResult pull = runGit("pull", "origin", branchName);
                if (pull.exitCode != 0) {
                    throw new GitSetupException("git pull origin " + branchName + " failed:\n"
                            + pull.output);
                }
                branchSummary = "Branch " + branchName + " checked out (pulled from origin).";
            } else {
                branchSummary = "Branch " + branchName
                        + " checked out (local-only branch, pull skipped).";
            }
        } else {
            listener.onStatus("Creating " + branchName + " from main...");
            GitResult checkoutMain = runGit("checkout", "main");
            if (checkoutMain.exitCode != 0) {
                throw new GitSetupException("git checkout main failed:\n" + checkoutMain.output);
            }
            GitResult pullMain = runGit("pull", "origin", "main");
            if (pullMain.exitCode != 0) {
                throw new GitSetupException("git pull origin main failed:\n" + pullMain.output);
            }
            GitResult createBranch = runGit("checkout", "-b", branchName);
            if (createBranch.exitCode != 0) {
                throw new GitSetupException("git checkout -b " + branchName + " failed:\n"
                        + createBranch.output);
            }
            branchSummary = "Branch " + branchName + " created from main.";
        }

        listener.onStatus("Copying staging files...");
        String copySummary = copyStagingFiles(showId, branchName, listener);

        return branchSummary + " " + copySummary + " Review and commit manually.";
    }

    private static final String[] COPY_SUBFOLDERS = { "boothsales", "reporting", "showfiles" };

    private String copyStagingFiles(String showId, String branchName, StatusListener listener)
            throws GitSetupException {
        String showSourceDir = this.stagingShowRoot + showId + "/";
        if (!new File(showSourceDir).isDirectory()) {
            throw new GitSetupException("Branch " + branchName
                    + " is ready, but staging folder not found: " + showSourceDir
                    + ". No files were copied.");
        }

        final StatusListener statusListener = listener;
        final int[] count = new int[1];
        List<String> copied = new ArrayList<String>();
        List<String> missing = new ArrayList<String>();
        for (String subfolder : COPY_SUBFOLDERS) {
            String sourceDir = showSourceDir + subfolder + "/";
            if (!new File(sourceDir).isDirectory()) {
                missing.add(subfolder);
                continue;
            }
            listener.onStatus("Copying " + subfolder + "...");
            final Path source = Paths.get(sourceDir);
            final Path target = Paths.get(
                    new File(this.repoDir, showId + "/" + subfolder).getPath());
            try {
                Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                            throws IOException {
                        Files.createDirectories(target.resolve(source.relativize(dir).toString()));
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        Files.copy(file, target.resolve(source.relativize(file).toString()),
                                StandardCopyOption.REPLACE_EXISTING);
                        count[0]++;
                        if (count[0] % 25 == 0) {
                            statusListener.onStatus("Copying files... " + count[0]);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                throw new GitSetupException("Branch " + branchName
                        + " is ready, but the copy from " + sourceDir + " failed after "
                        + count[0] + " files: " + e.getMessage());
            }
            copied.add(subfolder);
        }

        StringBuilder summary = new StringBuilder();
        if (copied.isEmpty()) {
            summary.append("No files copied — none of the expected subfolders (");
        } else {
            summary.append(count[0]).append(" files copied from Staging (");
            appendJoined(summary, copied);
            summary.append(") into MYS-Shows/").append(showId).append(".");
        }
        if (!missing.isEmpty()) {
            if (copied.isEmpty()) {
                appendJoined(summary, missing);
                summary.append(") were found under ").append(showSourceDir).append(".");
            } else {
                summary.append(" Not found on staging: ");
                appendJoined(summary, missing);
                summary.append(".");
            }
        }
        return summary.toString();
    }

    private static void appendJoined(StringBuilder sb, List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(items.get(i));
        }
    }

    private GitResult runGit(String... args) throws GitSetupException {
        List<String> command = new ArrayList<String>();
        command.add("git");
        for (String arg : args) {
            command.add(arg);
        }
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(this.repoDir);
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            reader.close();
            GitResult result = new GitResult();
            result.exitCode = process.waitFor();
            result.output = sb.toString().trim();
            return result;
        } catch (IOException e) {
            throw new GitSetupException("Could not run git — is git installed and on the PATH? ("
                    + e.getMessage() + ")");
        } catch (InterruptedException e) {
            throw new GitSetupException("Git command interrupted: " + e.getMessage());
        }
    }
}
