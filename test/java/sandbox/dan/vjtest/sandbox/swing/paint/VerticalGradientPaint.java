package dan.vjtest.sandbox.swing.paint;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

/**
 * @author Alexander Dovzhikov
 */
public class VerticalGradientPaint implements Paint {
    private final int topY;
    private final int bottomY;
    private final Color topColor;
    private final Color bottomColor;

    public VerticalGradientPaint(int topY, int bottomY, Color topColor, Color bottomColor) {
        this.topY = topY;
        this.bottomY = bottomY;
        this.topColor = topColor;
        this.bottomColor = bottomColor;
    }

    public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
        return new VerticalGradientPaintContext(cm, topY, bottomY, topColor, bottomColor);
    }

    public int getTransparency() {
        int topAlpha = topColor.getAlpha();
        int bottomAlpha = bottomColor.getAlpha();

        return ((topAlpha & bottomAlpha) == 0xff) ? OPAQUE : TRANSLUCENT;
    }
}
