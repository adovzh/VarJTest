package dan.vjtest.sandbox.swing.paint;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * @author Alexander Dovzhikov
 */
public class GradientPanel extends JPanel {
    private Color topColor = Color.BLUE;
    private Color bottomColor = new Color(0x7fff0000, true);

    public GradientPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension d = getSize();
        Graphics2D g2d = (Graphics2D) g;
        Rectangle rect = new Rectangle(0, 0, d.width, d.height);
        Area area = new Area(rect);
        area.subtract(new Area(new Ellipse2D.Float(d.width - d.height, 0, d.height, d.height)));

        g2d.setPaint(new VerticalGradientPaint(10, d.height - 10, topColor, bottomColor));
        g2d.fill(area);
    }
}
