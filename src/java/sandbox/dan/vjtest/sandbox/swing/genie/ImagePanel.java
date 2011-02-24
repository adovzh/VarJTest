package dan.vjtest.sandbox.swing.genie;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexander Dovzhikov
 */
public class ImagePanel extends JPanel {
    private final ImageIcon imageIcon;
    private final Dimension pref;

    public ImagePanel(String path) {
        this.imageIcon = new ImageIcon(getClass().getResource(path));
        this.pref = new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        return pref;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension d = getSize();

        g.drawImage(imageIcon.getImage(), 0, 0, d.width, d.height, this);
    }
}
