package quicklaunch.guis;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewShortcutBox extends JDialog {
    JTextField shortcut;

    JTextField shortcutPath;

    JButton addShortcutButton;

    public NewShortcutBox() {
        JPanel centerPanel = new JPanel(new FlowLayout(1, 0, 4));
        this.shortcutPath = new JTextField(12);
        this.shortcut = new JTextField(12);
        this.addShortcutButton = new JButton("Add");
        Font bigFont = this.shortcutPath.getFont().deriveFont(0, 20.0F);
        this.shortcutPath.setFont(bigFont);
        this.shortcut.setFont(bigFont);
        setSize(250, 175);
        setLocationRelativeTo((Component)null);
        centerPanel.add(new JLabel("Shortcut:"));
        centerPanel.add(this.shortcut);
        centerPanel.add(new JLabel("File Path:"));
        centerPanel.add(this.shortcutPath);
        centerPanel.add(this.addShortcutButton);
        Box theBox = Box.createVerticalBox();
        theBox.add(centerPanel);
        add(theBox);
        styleThisDialog();
    }

    public void styleThisDialog() {
        setAlwaysOnTop(true);
        setResizable(false);
        setVisible(true);
    }

    public void closeDialog() {
        dispose();
    }
}
