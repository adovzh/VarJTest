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

        String binaryString = toBinaryString(model.getValue());
        g.drawString(binaryString, 10, 10);
    }

    private static String toBinaryString(int value) {
        StringBuilder sb = new StringBuilder(40);
        int i;

        for (i = 31; i >= 0; i--) {
            sb.append(((value & (1 << i)) > 0) ? '1' : '0');

            if (i != 0 && (i & 3) == 0) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }
}
