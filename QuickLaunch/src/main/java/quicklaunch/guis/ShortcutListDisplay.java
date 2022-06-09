package quicklaunch.guis;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import quicklaunch.objects.Shortcut;
import quicklaunch.objects.ShortcutListCellRenderer;

public class ShortcutListDisplay extends JPanel {
    public DefaultListModel<Shortcut> model;

    public JList<Shortcut> list;

    public ShortcutListDisplay(ArrayList<Shortcut> shortcutList) {
        super(new BorderLayout());
        this.model = new DefaultListModel<>();
        Collections.sort(shortcutList);
        for (Shortcut sc : shortcutList)
            this.model.addElement(sc);
        this.list = new JList<>();
        this.list.setModel(this.model);
        this.list.setFixedCellHeight(75);
        this.list.setFixedCellWidth(370);
        this.list.setCellRenderer((ListCellRenderer<? super Shortcut>)new ShortcutListCellRenderer());
        JScrollPane jsp = new JScrollPane(this.list);
        add(jsp, "Center");
    }

    void display() {
        JDialog f = new JDialog();
        Box theBox = Box.createVerticalBox();
        theBox.add(this);
        f.add(theBox);
        f.pack();
        f.setLocationRelativeTo((Component)null);
        f.setVisible(true);
        f.setSize(410, 450);
        f.setResizable(false);
    }
}
