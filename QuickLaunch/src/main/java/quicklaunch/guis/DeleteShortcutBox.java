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

public class DeleteShortcutBox extends JDialog {
    JTextField shortcut;

    JButton deleteShortcutButton;

    public DeleteShortcutBox() {
        JPanel centerPanel = new JPanel(new FlowLayout(1, 0, 4));
        this.shortcut = new JTextField(12);
        this.deleteShortcutButton = new JButton("Delete");
        Font bigFont = this.shortcut.getFont().deriveFont(0, 20.0F);
        this.shortcut.setFont(bigFont);
        setSize(250, 125);
        setLocationRelativeTo((Component)null);
        centerPanel.add(new JLabel("Shortcut:"));
        centerPanel.add(this.shortcut);
        centerPanel.add(this.deleteShortcutButton);
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
