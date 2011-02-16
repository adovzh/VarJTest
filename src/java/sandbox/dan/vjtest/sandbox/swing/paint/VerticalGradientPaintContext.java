package dan.vjtest.sandbox.swing.paint;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.lang.ref.WeakReference;

/**
 * @author Alexander Dovzhikov
 */
public class VerticalGradientPaintContext implements PaintContext {
    private static final ColorModel XRGB_MODEL = new DirectColorModel(24, 0x00ff0000, 0x0000ff00, 0x000000ff);
    private static final ColorModel XBGR_MODEL = new DirectColorModel(24, 0x000000ff, 0x0000ff00, 0x00ff0000);

    private static ColorModel cachedModel;
    private static WeakReference<WritableRaster> cached;

    private static synchronized WritableRaster getCachedRaster(ColorModel colorModel, int w, int h) {
        if (colorModel == cachedModel && cached != null) {
            WritableRaster raster = cached.get();

            if (raster != null && raster.getWidth() >= w && raster.getHeight() >= h) {
                cached = null;
                return raster;
            }
        }

        return colorModel.createCompatibleWritableRaster(w, h);
    }

    private static synchronized void putCachedRaster(ColorModel colorModel, WritableRaster raster) {
        if (cached != null) {
            WritableRaster cachedRaster = cached.get();

            if (cachedRaster != null) {
                int cachedWidth = cachedRaster.getWidth();
                int cachedHeight = cachedRaster.getHeight();
                int width = raster.getWidth();
                int height = raster.getHeight();

                if (cachedWidth >= width && cachedHeight >= height)
                    return;

                if (cachedWidth * cachedHeight >= width * height)
                    return;
            }
        }

        cachedModel = colorModel;
        cached = new WeakReference<WritableRaster>(raster);
    }

    private ColorModel model;
    private WritableRaster saved;

    private final int topY;
    private final int bottomY;
    private final Color topColor;
    private final Color bottomColor;

    public VerticalGradientPaintContext(ColorModel colorModel, int topY, int bottomY, Color topColor, Color bottomColor) {
        this.topY = topY;
        this.bottomY = bottomY;
        this.topColor = topColor;
        this.bottomColor = bottomColor;

        int topAlpha = topColor.getAlpha();
        int bottomAlpha = bottomColor.getAlpha();

        if ((topAlpha & bottomAlpha) == 0xff) {
            model = XRGB_MODEL;

            if (colorModel instanceof DirectColorModel) {
                DirectColorModel dcm = (DirectColorModel) colorModel;
                int alphaMask = dcm.getAlphaMask();

                if ((alphaMask == 0 || alphaMask == 0xff)
                        && dcm.getRedMask() == 0xff
                        && dcm.getGreenMask() == 0xff00
                        && dcm.getBlueMask() == 0xff0000) {
                    model = XBGR_MODEL;
                }
            }
        } else {
            model = ColorModel.getRGBdefault();
        }
    }

    public void dispose() {
        if (saved != null) {
            putCachedRaster(model, saved);
            saved = null;
        }
    }

    public ColorModel getColorModel() {
        return model;
    }

    public Raster getRaster(int x, int y, int w, int h) {
        WritableRaster raster = saved;

        if (raster == null || raster.getWidth() < w || raster.getHeight() < h) {
            saved = raster = getCachedRaster(model, w, h);
        }

        int[] packedPixels = new int[w * h];
        int i = 0;
        int j = 0;

        for (int py = 0; py < h; py++) {
            int limitedY = Math.max(topY, Math.min(bottomY, y + py));
            float balance = ((float)(limitedY - topY)) / (bottomY - topY);
            Color color = mixDown(topColor, bottomColor, balance);
            for (int px = 0; px < w; px++) {
                packedPixels[j++] = color.getRGB();
            }
        }

        raster.setDataElements(0, 0, w, h, packedPixels);

        return raster;
    }

    private Color mixDown(Color c1, Color c2, float balance) {
        int red = (int) (c1.getRed() * (1 - balance) + c2.getRed() * balance);
        int green = (int) (c1.getGreen() * (1 - balance) + c2.getGreen() * balance);
        int blue = (int) (c1.getBlue() * (1 - balance) + c2.getBlue() * balance);
        int alpha = (int) (c1.getAlpha() * (1 - balance) + c2.getAlpha() * balance);

        return new Color(red, green, blue, alpha);
    }
}
