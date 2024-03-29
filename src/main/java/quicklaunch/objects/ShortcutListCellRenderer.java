package quicklaunch.objects;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ShortcutListCellRenderer extends JLabel implements ListCellRenderer {
    private static final long serialVersionUID = 1L;

    private static final Color HIGHLIGHT_COLOR = new Color(0, 252, 118);

    public ShortcutListCellRenderer() {
        setOpaque(true);
        setIconTextGap(12);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Shortcut entry = (Shortcut)value;
        setText(entry.toString());
        setIcon(entry.getIcon());
        if (isSelected) {
            setBackground(HIGHLIGHT_COLOR);
            setForeground(Color.black);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        return this;
    }
}
