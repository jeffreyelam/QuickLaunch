package quicklaunch.styles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollBar extends BasicScrollBarUI {
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {}

    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !this.scrollbar.isEnabled())
            return;
        g.translate(thumbBounds.x, thumbBounds.y);
        g.drawRect(0, 0, 18, 200);
        g.setColor(Color.black);
        g.fillRect(0, 0, 18, 200);
        g.setColor(Color.black);
        g.translate(-thumbBounds.x, -thumbBounds.y);
    }
}
