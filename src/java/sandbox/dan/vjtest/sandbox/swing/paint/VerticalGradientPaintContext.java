/*
 * Copyright (c) 2012 Alexander Dovzhikov <alexander.dovzhikov@gmail.com>.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY ALEXANDER DOVZHIKOV ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL dovzhikov OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * VerticalGradientPaintContext.java
 *
 * Created on 07.02.2012 17:29:28
 */

package dan.vjtest.sandbox.swing.paint;

import java.awt.*;
import java.awt.image.*;
import java.lang.ref.WeakReference;

/**
 * @author Alexander Dovzhikov
 */
public class VerticalGradientPaintContext implements PaintContext {
    private static final ColorModel XRGB_MODEL = new DirectColorModel(24, 0x00ff0000, 0x0000ff00, 0x000000ff);
    private static final ColorModel XBGR_MODEL = new DirectColorModel(24, 0x000000ff, 0x0000ff00, 0x00ff0000);

    private static ColorModel cachedModel;
    private static WeakReference<RasterBuffer> cached;

    private static synchronized RasterBuffer getCachedRaster(ColorModel colorModel, int w, int h) {
        if (colorModel == cachedModel && cached != null) {
            RasterBuffer buffer = cached.get();

            if (buffer != null && buffer.getLength() >= w * h) {
                cached = null;
                return buffer;
            }
        }

        return new RasterBuffer(colorModel, w, h);
    }

    private static synchronized void putCachedRaster(ColorModel colorModel, RasterBuffer buffer) {
        if (cached != null) {
            RasterBuffer cachedBuffer = cached.get();

            if (cachedBuffer != null && cachedBuffer.getLength() >= buffer.getLength()) {
                return;
            }
        }

        cachedModel = colorModel;
        cached = new WeakReference<RasterBuffer>(buffer);
    }

    private ColorModel model;
    private RasterBuffer saved;

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
        RasterBuffer buffer = saved;

        if (buffer == null || buffer.getLength() < w * h) {
            saved = buffer = getCachedRaster(model, w, h);
        }

        int[] packedPixels = buffer.getData();
        int j = 0;

        for (int py = 0; py < h; py++) {
            int limitedY = Math.max(topY, Math.min(bottomY, y + py));
            float balance = ((float)(limitedY - topY)) / (bottomY - topY);
            Color color = mixDown(topColor, bottomColor, balance);
            for (int px = 0; px < w; px++) {
                packedPixels[j++] = color.getRGB();
            }
        }

        return buffer.getRaster();
    }

    private Color mixDown(Color c1, Color c2, float balance) {
        int red = (int) (c1.getRed() * (1 - balance) + c2.getRed() * balance);
        int green = (int) (c1.getGreen() * (1 - balance) + c2.getGreen() * balance);
        int blue = (int) (c1.getBlue() * (1 - balance) + c2.getBlue() * balance);
        int alpha = (int) (c1.getAlpha() * (1 - balance) + c2.getAlpha() * balance);

        return new Color(red, green, blue, alpha);
    }
}
