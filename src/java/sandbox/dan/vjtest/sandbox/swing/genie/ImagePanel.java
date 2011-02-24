package dan.vjtest.sandbox.swing.genie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * @author Alexander Dovzhikov
 */
public class ImagePanel extends JPanel implements AnimationTracker {
    private final ImageIcon imageIcon;
    private final Dimension pref;
    private final Genie genie;
    private final Animation animation = new Animation();
    private boolean dirty = true;
    Image img;

    public ImagePanel(String path) {
        this.imageIcon = new ImageIcon(getClass().getResource(path));
        this.pref = new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        this.genie = new Genie(0.2, 0.3);
        genie.setSourceImage(createBufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
        animationAdvanced(0);
        animation.setListener(this);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                dirty = true;
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (dirty) {
                    Dimension d = getSize();
                    genie.setSourceImage(createBufferedImage(d.width, d.height));
                }

                animation.animate();
            }
        });

        setOpaque(false);
    }

    private BufferedImage createBufferedImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();
//        paintComponent(g);
//        imageIcon.paintIcon(this, g, 0, 0);
        g.drawImage(imageIcon.getImage(), 0, 0, width, height, this);
        g.dispose();

        return image;
    }

    @Override
    public Dimension getPreferredSize() {
        return pref;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension d = getSize();

//        System.out.println("Draw image");
        g.drawImage(img, 0, 0, d.width, d.height, this);
    }

    public void animationAdvanced(double p) {
        img = genie.getImage(p);
        repaint();
    }
}
