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
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import quicklaunch.objects.Directory;
import quicklaunch.objects.Shortcut;

public class InputBox extends JDialog implements IntellitypeListener, HotkeyListener {
    private static final long serialVersionUID = 1L;

    int pX;

    int pY;

    private Directory shortCutMap;

    public JTextField userInput;

    public JLabel environmentLabel;

    public String environment;

    public String filePath;

    private ShortcutListDisplay shortcutDisplay;

    private JPopupMenu menu;

    private JMenuItem openItem;

    private JMenuItem deleteItem;

    private JMenuItem exitItem;

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
        this.filePath = "Y:/mys_shared/";
        centerPanel.add(this.environmentLabel);
        centerPanel.add(this.userInput);
        Box theBox = Box.createVerticalBox();
        theBox.add(centerPanel);
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
        setSize(275, 45);
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
        } else if ((shortcut.split(":")).length <= 1) {
            if (shortcut.equalsIgnoreCase("admin")) {
                if (this.environment.equalsIgnoreCase("staging"))
                    try {
                        this.shortCutMap.openDirectory("Y:/mys/admin.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("test"))
                    try {
                        this.shortCutMap.openDirectory("X:/Test/mys/admin.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("dev"))
                    try {
                        this.shortCutMap.openDirectory("X:/Dev/mys/admin.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("local"))
                    try {
                        this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/admin.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else if (shortcut.equalsIgnoreCase("api")) {
                if (this.environment.equalsIgnoreCase("staging"))
                    try {
                        this.shortCutMap.openDirectory("Y:/mys/api.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("test"))
                    try {
                        this.shortCutMap.openDirectory("X:/Test/mys/api.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("dev"))
                    try {
                        this.shortCutMap.openDirectory("X:/Dev/mys/api.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("local"))
                    try {
                        this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/api.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else if (shortcut.equalsIgnoreCase("gardner")) {
                if (this.environment.equalsIgnoreCase("staging"))
                    try {
                        this.shortCutMap.openDirectory("Y:/mys/gardner.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("test"))
                    try {
                        this.shortCutMap.openDirectory("X:/Test/mys/gardner.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("dev"))
                    try {
                        this.shortCutMap.openDirectory("X:/Dev/mys/gardner.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("local"))
                    try {
                        this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/gardner.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else if (shortcut.equalsIgnoreCase("maint")) {
                if (this.environment.equalsIgnoreCase("staging"))
                    try {
                        this.shortCutMap.openDirectory("Y:/mys/maint.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("test"))
                    try {
                        this.shortCutMap.openDirectory("X:/Test/mys/maint.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("dev"))
                    try {
                        this.shortCutMap.openDirectory("X:/Dev/mys/maint.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("local"))
                    try {
                        this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/maint.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else if (shortcut.equalsIgnoreCase("mys")) {
                if (this.environment.equalsIgnoreCase("staging"))
                    try {
                        this.shortCutMap.openDirectory("Y:/mys/mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("test"))
                    try {
                        this.shortCutMap.openDirectory("X:/Test/mys/mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("dev"))
                    try {
                        this.shortCutMap.openDirectory("X:/Dev/mys/mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("local"))
                    try {
                        this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else if (shortcut.equalsIgnoreCase("mobile")) {
                if (this.environment.equalsIgnoreCase("staging"))
                    try {
                        this.shortCutMap.openDirectory("Y:/mys/mobile.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("test"))
                    try {
                        this.shortCutMap.openDirectory("X:/Test/mys/mobile.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("dev"))
                    try {
                        this.shortCutMap.openDirectory("X:/Dev/mys/mobile.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("local"))
                    try {
                        this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/mobile.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else if (shortcut.equalsIgnoreCase("vts") || shortcut.equalsIgnoreCase("ids")) {
                if (this.environment.equalsIgnoreCase("staging"))
                    try {
                        this.shortCutMap.openDirectory("Y:/mys/vts.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("test"))
                    try {
                        this.shortCutMap.openDirectory("X:/Test/mys/vts.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("dev"))
                    try {
                        this.shortCutMap.openDirectory("X:/Dev/mys/vts.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("local"))
                    try {
                        this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/vts.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else if (shortcut.equalsIgnoreCase("cm")) {
                if (this.environment.equalsIgnoreCase("staging"))
                    try {
                        this.shortCutMap.openDirectory("Y:/mys/cm.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("test"))
                    try {
                        this.shortCutMap.openDirectory("X:/Test/mys/cm.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("dev"))
                    try {
                        this.shortCutMap.openDirectory("X:/Dev/mys/cm.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("local"))
                    try {
                        this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/cm.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else if (shortcut.equalsIgnoreCase("exh")) {
                if (this.environment.equalsIgnoreCase("staging"))
                    try {
                        this.shortCutMap.openDirectory("Y:/mys/exh.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("test"))
                    try {
                        this.shortCutMap.openDirectory("X:/Test/mys/exh.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("dev"))
                    try {
                        this.shortCutMap.openDirectory("X:/Dev/mys/exh.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("local"))
                    try {
                        this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/exh.mapyourshow.com/wwwroot/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else if (shortcut.equalsIgnoreCase("add")) {
                final NewShortcutBox shortcutData = new NewShortcutBox();
                shortcutData.addShortcutButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (shortcutData.shortcut.getText().length() > 0 && shortcutData.shortcutPath.getText().length() > 0) {
                            try {
                                InputBox.this.shortCutMap.addShortCut(shortcutData.shortcut.getText(), (shortcutData.shortcutPath.getText().indexOf("http") < 0) ? new File(shortcutData.shortcutPath.getText().replace("\"", "")) : null, (shortcutData.shortcutPath.getText().indexOf("http") == 0) ? new URL(shortcutData.shortcutPath.getText()) : null);
                                InputBox.this.saveDirectory(InputBox.this.shortCutMap);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            shortcutData.closeDialog();
                        }
                    }
                });
            } else if (shortcut.equalsIgnoreCase("delete")) {
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
            } else if (shortcut.equalsIgnoreCase("shortcuts")) {
                this.shortcutDisplay = new ShortcutListDisplay(this.shortCutMap.getShortcuts());
                this.shortcutDisplay.list.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        String shortcutSelected = ((Shortcut)InputBox.this.shortcutDisplay.list.getSelectedValue()).getShortcut().toString().toLowerCase();
                        if (evt.getClickCount() == 2) {
                            if (SwingUtilities.isLeftMouseButton(evt))
                                try {
                                    InputBox.this.shortCutMap.openShortcut(shortcutSelected);
                                } catch (IOException|NullPointerException|IllegalArgumentException iOException) {}
                        } else if (SwingUtilities.isRightMouseButton(evt)) {
                            InputBox.this.createPopupMenu(shortcutSelected, evt);
                        }
                    }
                });
                this.shortcutDisplay.display();
            } else if (shortcut.equalsIgnoreCase("local")) {
                this.environmentLabel.setText("LOCAL     ");
                this.filePath = "C:/inetpub/wwwroot/mys_shared/";
                this.environment = "local";
            } else if (shortcut.equalsIgnoreCase("dev")) {
                this.environmentLabel.setText("DEV       ");
                this.filePath = "X:/Dev/mys_shared/";
                this.environment = "dev";
            } else if (shortcut.equalsIgnoreCase("test")) {
                this.environmentLabel.setText("TEST      ");
                this.filePath = "X:/Test/mys_shared/";
                this.environment = "test";
            } else if (shortcut.equalsIgnoreCase("staging")) {
                this.environmentLabel.setText("STAGING   ");
                this.filePath = "Y:/mys_shared/";
                this.environment = "staging";
            } else if (shortcut.equalsIgnoreCase("core")) {
                if (this.environment.equalsIgnoreCase("staging"))
                    try {
                        this.shortCutMap.openDirectory("Y:/mys/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("test"))
                    try {
                        this.shortCutMap.openDirectory("X:/Test/mys/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("dev"))
                    try {
                        this.shortCutMap.openDirectory("X:/Dev/mys/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (this.environment.equalsIgnoreCase("local"))
                    try {
                        this.shortCutMap.openDirectory("C:/inetpub/wwwroot/mys/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } else if (this.shortCutMap.keyExists(shortcut.toLowerCase())) {
                try {
                    this.shortCutMap.openShortcut(shortcut.toLowerCase());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                hideThis();
            } else {
                try {
                    this.shortCutMap.openDirectory(this.filePath + shortcut.toLowerCase());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addShortcutListener() {
        this.userInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10 && InputBox.this.userInput.getText().length() > 0) {
                    String[] shortcutList = InputBox.this.userInput.getText().split(",");
                    for (int i = 0; i < shortcutList.length; i++)
                        InputBox.this.tryShortcut(shortcutList[i]);
                    InputBox.this.userInput.setText("");
                }
            }
        });
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
                    InputBox.this.shortCutMap.openShortcut(scSelected);
                } catch (IOException e1) {
                    e1.printStackTrace();
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
