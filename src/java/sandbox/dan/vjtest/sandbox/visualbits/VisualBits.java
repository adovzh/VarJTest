package dan.vjtest.sandbox.visualbits;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexander Dovzhikov
 */
public class VisualBits extends JPanel {
    private final VisualBitsModel model;

    public VisualBits(VisualBitsModel model) {
        this.model = model;
        model.addVisualBitsListener(new VisualBitsListener() {
            public void modelChanged() {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        String binaryString = Integer.toBinaryString(model.getValue());
        g.drawString(binaryString, 10, 10);
    }
}
