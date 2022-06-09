package quicklaunch.objects;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ShortcutMenu extends JPopupMenu {
    JMenuItem openItem;

    JMenuItem deleteItem;

    JMenuItem exitItem;

    public ShortcutMenu() {
        this.openItem = new JMenuItem("OPEN SHORTCUT");
        this.deleteItem = new JMenuItem("DELETE SHORTCUT");
        this.exitItem = new JMenuItem("EXIT");
        add(this.openItem);
        add(this.deleteItem);
        add(this.exitItem);
    }
}
