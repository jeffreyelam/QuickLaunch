package quicklaunch.objects;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class Directory extends HashMap<String, Shortcut> implements Serializable {
    public String localPath = "C:/inetpub/wwwroot/";

    public String developmentPath = "//amznfsxbl1a2mn7.mysweb.net/share/Development/";

    public String testPath = "//amznfsxsffi9ljm.mysweb.net/share/Test/";

    public String stagingPath =  "//amznfsxpms4nnk0.mysweb.net/share/Staging/";

    public String productionPath = "//amznfsxotr4sl7t.mysweb.net/share/Production/";

    public static void openFile(File file) throws IOException {
        Desktop dt = Desktop.getDesktop();
        dt.open(file);
    }

    public void openShortcut(String shortcut, String currentEnvironment) throws IOException {
        try {
            if (get(shortcut).getFile() != null)
            {
                if(get(shortcut).getIsEnvironmentSpecific())
                {
                    String environmentSpecificPath = get(shortcut).getFile().getPath();
                    environmentSpecificPath = environmentSpecificPath.replace("\\", "/");
                    environmentSpecificPath = environmentSpecificPath.replace(this.productionPath, "");
                    environmentSpecificPath = environmentSpecificPath.replace(this.stagingPath, "");
                    environmentSpecificPath = environmentSpecificPath.replace(this.testPath, "");
                    environmentSpecificPath = environmentSpecificPath.replace(this.developmentPath, "");
                    environmentSpecificPath = environmentSpecificPath.replace(this.localPath, "");
                    File environmentSpecificFile = new File(currentEnvironment + environmentSpecificPath);
                    openFile(environmentSpecificFile);
                }
                else
                {
                    openFile(get(shortcut).getFile());
                }
            } else if (get(shortcut).getURL() != null) {
                openWebpage(get(shortcut).getURL());
            }
        } catch (IOException|NullPointerException e) {
            System.out.println("Shortcut not found!");
        }
    }

    public void openDirectory(String directoryPath) throws IOException {
        File directoryAsFile = new File(directoryPath);
        try {
            openFile(directoryAsFile);
        } catch (IllegalArgumentException ignored) {}
    }

    public void deleteShortcut(String shortcut) {
        remove(shortcut);
    }

    public void addShortCut(String shortCut, File file, URL url, Boolean isEnvironmentSpecific) {
        Shortcut shortcutToAdd = new Shortcut(shortCut, file, url, isEnvironmentSpecific);
        put(shortCut, shortcutToAdd);
    }

    public void outputDirectory() {
        JOptionPane.showMessageDialog(null, null, "Shortcuts:",
                1);
    }

    public ArrayList<String> getShortcutKeys() {
        ArrayList<String> returnStringArray = new ArrayList<>();
        for (String key : keySet())
            returnStringArray.add(key);
        return returnStringArray;
    }

    public ArrayList<Shortcut> getShortcuts() {
        ArrayList<Shortcut> shortcutReturnList = new ArrayList<>();
        for (String key : getShortcutKeys())
            shortcutReturnList.add(get(key));
        return shortcutReturnList;
    }

    public boolean keyExists(String key) {
        if (containsKey(key))
            return true;
        return false;
    }

    public void openWebpage(URL urlPath) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
            try {
                desktop.browse(urlPath.toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void openEmail() {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.MAIL)) {
            try {
                URI mailto = new URI("mailto:?subject=SUBJECT&body=BODY");
                desktop.mail(mailto);
            } catch (IOException|java.net.URISyntaxException iOException) {}
        } else {
            throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
        }
    }

    public Shortcut getShortcutData(String key) {
        return get(key);
    }
}
