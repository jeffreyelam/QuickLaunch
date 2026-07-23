package quicklaunch.guis;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.IntellitypeListener;
import com.melloware.jintellitype.JIntellitype;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import quicklaunch.objects.Directory;
import quicklaunch.objects.GitShowSetup;
import quicklaunch.objects.Shortcut;
import java.awt.Desktop;
import java.net.URI;

public class InputBox extends JDialog implements IntellitypeListener, HotkeyListener {
    private static final long serialVersionUID = 1L;

    int pX;

    int pY;

    private Directory shortCutMap;

    public JTextField userInput;

    public JLabel environmentLabel;

    public String environment;

    public String filePath;

    public String imtsPath;

    public String localPath;

    public String developmentPath;

    public String testPath;

    public String stagingPath;

    public String productionPath;

    public String gitRepoPath;

    public String gitStagingSourcePath;

    private ShortcutListDisplay shortcutDisplay;

    private JPopupMenu menu;

    private JMenuItem openItem;

    private JMenuItem deleteItem;

    private JMenuItem exitItem;

    private Desktop desk;

    private JLabel statusLabel;

    private javax.swing.Timer statusTimer;

    private final List<String> commandHistory = new ArrayList<String>();

    private int historyIndex = 0;

    /** A file-system / network operation that may block or fail; returns an optional
     *  status message to show on success (or null), and may throw to report failure. */
    private interface IoTask {
        String run() throws Exception;
    }

    private static final String[][] COMMANDS = {
        { "help / ?", "Show this list of commands" },
        { "local / dev / test / staging (stage) / production / imts", "Switch the active environment" },
        { "add", "Add a shortcut (name + file path or URL)" },
        { "delete", "Delete a shortcut by name" },
        { "shortcuts", "List saved shortcuts (double-click opens, right-click manages)" },
        { "root", "Open the current environment's root folder" },
        { "core", "Open the current environment's mys/ folder" },
        { "exh", "(local only) Open MYS-ExhDashboard" },
        { "git:<showId>", "Set up a MYS-Shows branch for a show and copy staging files" },
        { "ip:<address>", "Open a whois lookup for an IP/host in the browser" },
        { "<shortcut name>", "Open a saved shortcut" },
        { "<any text>", "Fuzzy-search folders in the current environment and open a match" },
        { "a,b,c", "Run several commands at once, separated by commas" },
        { "exit", "Save shortcuts and quit QuickLaunch" },
    };

    public InputBox() {
        try {
            this.shortCutMap = loadDirectory();
        } catch (ClassNotFoundException|IOException e2) {
            this.shortCutMap = new Directory();
        }
        JPanel centerPanel = new JPanel(new FlowLayout(1, 0, 4));
        this.userInput = new JTextField();
        this.userInput.setPreferredSize(new Dimension(200, 30));
        this.userInput.setFont(this.userInput.getFont().deriveFont(0, 20.0F));
        this.userInput.setHorizontalAlignment(0);
        this.environmentLabel = new JLabel("STAGING   ");
        this.environment = "staging";
        this.imtsPath = "//amznfsxge5zxgzv.mysweb.net/share/IMTS/";
        this.productionPath = "//amznfsxotr4sl7t.mysweb.net/share/Production/";
        this.stagingPath = "//amznfsxpms4nnk0.mysweb.net/share/Staging/";
        this.testPath = "//amznfsxsffi9ljm.mysweb.net/share/Test/";
        this.developmentPath = "//amznfsxbl1a2mn7.mysweb.net/share/Development/";
        this.localPath = "C:/inetpub/wwwroot/github/";
        this.gitRepoPath = this.localPath + "MYS-Shows";
        this.gitStagingSourcePath = this.stagingPath + "MYS_Shared/";
        this.filePath = this.stagingPath;
        this.desk = Desktop.getDesktop();
        centerPanel.add(this.environmentLabel);
        centerPanel.add(this.userInput);
        this.statusLabel = new JLabel(" ");
        this.statusLabel.setFont(this.statusLabel.getFont().deriveFont(Font.PLAIN, 11.0F));
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        statusPanel.add(this.statusLabel);
        Box theBox = Box.createVerticalBox();
        theBox.add(centerPanel);
        theBox.add(statusPanel);
        add(theBox);
        styleThisDialog();
        addShortcutListener();
        makeJFrameMove();
    }

    private void moveThisDialog(MouseEvent mEvent) {
        setLocation((getLocation()).x + mEvent.getX() - this.pX,
                (getLocation()).y + mEvent.getY() - this.pY);
    }

    private void closeDialog() {
        dispose();
    }

    private void styleThisDialog() {
        setSize(275, 68);
        setLocationRelativeTo(null);
        setResizable(false);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        setUndecorated(true);
        setVisible(true);
    }

    private void saveDirectory(Directory direct) throws IOException {
        FileOutputStream fos = new FileOutputStream(
                "shortcutDirectory.directFile");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(direct);
        oos.close();
    }

    private Directory loadDirectory() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(
                "shortcutDirectory.directFile");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Directory returnDirectory = (Directory)ois.readObject();
        ois.close();
        return returnDirectory;
    }

    public void tryShortcut(String shortcut) {
        if (shortcut.equalsIgnoreCase("exit")) {
            try {
                saveDirectory(this.shortCutMap);
            } catch (IOException iOException) {}
            JIntellitype.getInstance().cleanUp();
            System.exit(0);
            closeDialog();
        }
        else if (shortcut.equalsIgnoreCase("help") || shortcut.equals("?"))
        {
            showHelpDialog();
        }
        else if (shortcut.equalsIgnoreCase("exh") && this.environment.equalsIgnoreCase("local"))
        {
            openAsync("Open ExhDashboard", new IoTask() {
                public String run() throws Exception {
                    InputBox.this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/MYS-ExhDashboard");
                    return null;
                }
            });
        }
        else if (shortcut.equalsIgnoreCase("add"))
        {
            final NewShortcutBox shortcutData = new NewShortcutBox();
            shortcutData.addShortcutButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (shortcutData.shortcut.getText().length() > 0 && shortcutData.shortcutPath.getText().length() > 0) {
                        try
                        {
                            String newFilePath = shortcutData.shortcutPath.getText();


                            boolean isUrl = newFilePath.startsWith("http");
                            InputBox.this.shortCutMap.addShortCut(shortcutData.shortcut.getText(), isUrl ? null : new File(newFilePath.replace("\"", "")), isUrl ? new URL(newFilePath) : null, shortcutData.isEnvironmentSpecific.isSelected());
                            InputBox.this.saveDirectory(InputBox.this.shortCutMap);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        shortcutData.closeDialog();
                    }
                }
            });
        }
        else if (shortcut.equalsIgnoreCase("delete"))
        {
            final DeleteShortcutBox shortcutDelete = new DeleteShortcutBox();
            shortcutDelete.deleteShortcutButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (shortcutDelete.shortcut.getText().length() > 0 && InputBox.this.shortCutMap.keyExists(shortcutDelete.shortcut.getText())) {
                        InputBox.this.shortCutMap.deleteShortcut(shortcutDelete.shortcut.getText());
                        shortcutDelete.closeDialog();
                        try {
                            InputBox.this.saveDirectory(InputBox.this.shortCutMap);
                        } catch (IOException iOException) {}
                    }
                }
            });
        }
        else if ( shortcut.substring(0, Math.min(shortcut.length(), 3)).equalsIgnoreCase("ip:") )
        {
            // now we enter our URL that we want to open in our
            // default browser
            try {
                desk.browse(new URI("https://www.whois.com/whois/" + shortcut.toLowerCase().replace("ip:","")));
            } catch (Exception e) {
                // do nothing
            }
        }
        else if ( shortcut.substring(0, Math.min(shortcut.length(), 4)).equalsIgnoreCase("git:") )
        {
            final String showId = shortcut.substring(Math.min(shortcut.length(), 4)).trim();
            if (!GitShowSetup.isValidShowId(showId)) {
                JOptionPane.showMessageDialog(this, "Invalid show id '" + showId
                        + "' - letters, numbers, dash and underscore only.",
                        "git:", JOptionPane.ERROR_MESSAGE);
            } else {
                final GitStatusBox statusBox = new GitStatusBox();
                statusBox.setStatus("Preparing branch " + showId + "...");
                new SwingWorker<String, String>() {
                    protected String doInBackground() throws Exception {
                        GitShowSetup setup = new GitShowSetup(
                                InputBox.this.gitRepoPath, InputBox.this.gitStagingSourcePath);
                        return setup.setUpShow(showId, new GitShowSetup.StatusListener() {
                            public void onStatus(String message) {
                                publish(message);
                            }
                        });
                    }

                    protected void process(java.util.List<String> chunks) {
                        statusBox.setStatus(chunks.get(chunks.size() - 1));
                    }

                    protected void done() {
                        statusBox.closeDialog();
                        try {
                            JOptionPane.showMessageDialog(InputBox.this, get(),
                                    "git:" + showId, JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e) {
                            Throwable cause = (e.getCause() != null) ? e.getCause() : e;
                            JOptionPane.showMessageDialog(InputBox.this, cause.getMessage(),
                                    "git:" + showId + " failed", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }.execute();
            }
        }
        else if (shortcut.equalsIgnoreCase("shortcuts"))
        {
            this.shortcutDisplay = new ShortcutListDisplay(this.shortCutMap.getShortcuts());
            this.shortcutDisplay.list.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    String shortcutSelected = ((Shortcut)InputBox.this.shortcutDisplay.list.getSelectedValue()).getShortcut().toString().toLowerCase();
                    if (evt.getClickCount() == 2) {
                        if (SwingUtilities.isLeftMouseButton(evt))
                            try {
                                InputBox.this.shortCutMap.openShortcut(shortcutSelected, InputBox.this.filePath);
                            } catch (IOException|NullPointerException|IllegalArgumentException ex) {
                                InputBox.this.showStatus("Couldn't open '" + shortcutSelected + "': " + ex.getMessage());
                            }
                    } else if (SwingUtilities.isRightMouseButton(evt)) {
                        InputBox.this.createPopupMenu(shortcutSelected, evt);
                    }
                }
            });
            this.shortcutDisplay.display();
        } else if (shortcut.equalsIgnoreCase("local")) {
            this.environmentLabel.setText("LOCAL     ");
            this.filePath = this.localPath;
            this.environment = "local";
        } else if (shortcut.equalsIgnoreCase("dev")) {
            this.environmentLabel.setText("DEV       ");
            this.filePath = this.developmentPath;
            this.environment = "dev";
        } else if (shortcut.equalsIgnoreCase("test")) {
            this.environmentLabel.setText("TEST      ");
            this.filePath = this.testPath;
            this.environment = "test";
        } else if (shortcut.equalsIgnoreCase("staging") || shortcut.equalsIgnoreCase("stage")) {
            this.environmentLabel.setText("STAGING   ");
            this.filePath = this.stagingPath;
            this.environment = "staging";
        } else if (shortcut.equalsIgnoreCase("production")) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Switch to PRODUCTION? Actions will target the live production share.",
                    "Confirm PRODUCTION", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                this.environmentLabel.setText("**PROD**   ");
                this.filePath = this.productionPath;
                this.environment = "production";
            }
        } else if (shortcut.equalsIgnoreCase("imts")) {
            this.environmentLabel.setText("IMTS   ");
            this.filePath = this.imtsPath;
            this.environment = "imts";
        } else if (shortcut.equalsIgnoreCase("root"))
        {
            openAsync("Open root", new IoTask() {
                public String run() throws Exception {
                    InputBox.this.shortCutMap.openDirectory(InputBox.this.filePath);
                    return null;
                }
            });
        } else if (shortcut.equalsIgnoreCase("core"))
        {
            openAsync("Open core", new IoTask() {
                public String run() throws Exception {
                    InputBox.this.shortCutMap.openDirectory(InputBox.this.filePath + "mys/");
                    return null;
                }
            });
        } else if (this.shortCutMap.keyExists(shortcut.toLowerCase())) {
            final String key = shortcut.toLowerCase();
            hideThis();
            openAsync("Open '" + key + "'", new IoTask() {
                public String run() throws Exception {
                    InputBox.this.shortCutMap.openShortcut(key, InputBox.this.filePath);
                    return null;
                }
            });
        } else {
            final String query = shortcut.toLowerCase();
            openAsync("Open '" + shortcut + "'", new IoTask() {
                public String run() throws Exception {
                    if (InputBox.this.environment.equalsIgnoreCase("local")) {
                        boolean found = InputBox.this.openMatches(
                                InputBox.this.filePath + "MYS-Shared/", query);
                        if (!found) {
                            found = InputBox.this.openMatches(
                                    InputBox.this.filePath + "MYS-Shows/", query);
                        }
                        if (!found) {
                            return "No match for '" + query + "' in local MYS-Shared / MYS-Shows.";
                        }
                        return null;
                    }
                    try {
                        InputBox.this.shortCutMap.openDirectory(
                                InputBox.this.filePath + "MYS_Shared/" + query);
                    } catch (IOException e) {
                        InputBox.this.shortCutMap.openDirectory(InputBox.this.filePath + query);
                    }
                    return null;
                }
            });
        }
    }

    /** Opens every folder under {@code folderPath} whose path contains {@code query}.
     *  Returns true if at least one was opened. Throws if the folder can't be listed
     *  (e.g. an unmounted / unreachable network share), instead of NPE-ing on null. */
    private boolean openMatches(String folderPath, String query) throws IOException {
        File[] entries = new File(folderPath).listFiles();
        if (entries == null) {
            throw new IOException("Share unavailable or not a folder: " + folderPath);
        }
        boolean found = false;
        for (File file : entries) {
            if (file.getPath().toLowerCase().contains(query)) {
                this.shortCutMap.openDirectory(file.getPath());
                found = true;
            }
        }
        return found;
    }

    /** Runs a blocking file/network task off the Event Dispatch Thread so a slow or
     *  unreachable share can't freeze the UI, and surfaces success/failure in the
     *  status line. */
    private void openAsync(final String description, final IoTask task) {
        new SwingWorker<String, Void>() {
            protected String doInBackground() throws Exception {
                return task.run();
            }

            protected void done() {
                try {
                    String message = get();
                    if (message != null) {
                        showStatus(message);
                    }
                } catch (Exception e) {
                    Throwable cause = (e.getCause() != null) ? e.getCause() : e;
                    showStatus(description + " failed: " + cause.getMessage());
                }
            }
        }.execute();
    }

    /** Shows a short, auto-clearing message in the status line under the input. */
    private void showStatus(String message) {
        this.statusLabel.setText((message == null || message.length() == 0) ? " " : message);
        if (this.statusTimer == null) {
            this.statusTimer = new javax.swing.Timer(4000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    InputBox.this.statusLabel.setText(" ");
                }
            });
            this.statusTimer.setRepeats(false);
        }
        this.statusTimer.restart();
    }

    private void showHelpDialog() {
        StringBuilder sb = new StringBuilder();
        sb.append("QuickLaunch (Ctrl+J to focus; Up/Down for history)\n\n");
        for (String[] command : COMMANDS) {
            sb.append(command[0]).append("\n    ").append(command[1]).append("\n\n");
        }
        JTextArea area = new JTextArea(sb.toString(), 18, 64);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setCaretPosition(0);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(560, 380));
        JOptionPane.showMessageDialog(this, scroll, "QuickLaunch - Commands",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void addShortcutListener() {
        this.userInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ENTER && InputBox.this.userInput.getText().length() > 0) {
                    String raw = InputBox.this.userInput.getText();
                    InputBox.this.commandHistory.add(raw);
                    InputBox.this.historyIndex = InputBox.this.commandHistory.size();
                    String[] shortcutList = raw.split(",");
                    for (int i = 0; i < shortcutList.length; i++)
                        InputBox.this.tryShortcut(shortcutList[i]);
                    InputBox.this.userInput.setText("");
                } else if (code == KeyEvent.VK_UP) {
                    InputBox.this.recallHistory(-1);
                    e.consume();
                } else if (code == KeyEvent.VK_DOWN) {
                    InputBox.this.recallHistory(1);
                    e.consume();
                }
            }
        });
    }

    /** Cycles the input field through previously entered commands. */
    private void recallHistory(int direction) {
        if (this.commandHistory.isEmpty()) {
            return;
        }
        int newIndex = this.historyIndex + direction;
        if (newIndex < 0) {
            newIndex = 0;
        }
        if (newIndex >= this.commandHistory.size()) {
            this.historyIndex = this.commandHistory.size();
            this.userInput.setText("");
            return;
        }
        this.historyIndex = newIndex;
        this.userInput.setText(this.commandHistory.get(newIndex));
    }

    private void makeJFrameMove() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                InputBox.this.pX = me.getX();
                InputBox.this.pY = me.getY();
            }

            public void mouseDragged(MouseEvent me) {
                InputBox.this.moveThisDialog(me);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
                InputBox.this.moveThisDialog(me);
            }
        });
    }

    public void onHotKey(int aIdentifier) {
        toFront();
        this.userInput.requestFocus();
    }

    public void onIntellitype(int aCommand) {}

    private void hideThis() {
        toBack();
    }

    public void changeEnvironment(String newEnvironment) {
        this.environmentLabel.setText(newEnvironment);
    }

    public void createPopupMenu(final String scSelected, MouseEvent mouseEvt) {
        this.menu = new JPopupMenu();
        this.openItem = new JMenuItem("OPEN SHORTCUT");
        this.openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    InputBox.this.shortCutMap.openShortcut(scSelected, InputBox.this.filePath);
                } catch (IOException e1) {
                    InputBox.this.showStatus("Couldn't open '" + scSelected + "': " + e1.getMessage());
                }
            }
        });
        this.deleteItem = new JMenuItem("DELETE SHORTCUT");
        this.deleteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InputBox.this.shortCutMap.deleteShortcut(scSelected);
                try {
                    InputBox.this.saveDirectory(InputBox.this.shortCutMap);
                } catch (IOException iOException) {}
                InputBox.this.shortcutDisplay.model.removeElement(InputBox.this.shortcutDisplay.list.getSelectedValue());
            }
        });
        this.exitItem = new JMenuItem("EXIT");
        this.exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InputBox.this.menu.setVisible(false);
            }
        });
        this.menu.add(this.openItem);
        this.menu.add(this.deleteItem);
        this.menu.add(this.exitItem);
        this.menu.show(mouseEvt.getComponent(), mouseEvt.getX(), mouseEvt.getY());
    }
}
