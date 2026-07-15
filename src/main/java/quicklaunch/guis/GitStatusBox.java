package quicklaunch.guis;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GitStatusBox extends JDialog {
    JLabel status;

    public GitStatusBox() {
        JPanel centerPanel = new JPanel(new FlowLayout(1, 0, 4));
        this.status = new JLabel(" ");
        this.status.setFont(this.status.getFont().deriveFont(0, 14.0F));
        setSize(320, 75);
        setLocationRelativeTo((Component)null);
        centerPanel.add(this.status);
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

    public void setStatus(String message) {
        this.status.setText(message);
    }

    public void closeDialog() {
        dispose();
    }
}
