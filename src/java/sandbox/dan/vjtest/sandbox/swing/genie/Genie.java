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
 * Genie.java
 *
 * Created on 07.02.2012 17:29:28
 */

package dan.vjtest.sandbox.swing.genie;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * @author Alexander Dovzhikov
 */
public class Genie {
    private final double p1;
    private final double p2;
    private Raster sourceRaster = null;

    public Genie(double p1, double p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void setSourceImage(BufferedImage sourceImage) {
        sourceRaster = sourceImage.getRaster();
    }

    public Image getImage(double p) {
        if (sourceRaster == null)
            return null;

        int w = sourceRaster.getWidth();
        int h = sourceRaster.getHeight();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = image.getRaster();

        int[] sourceRow = new int[w];
        int[] row = new int[w];
        int x1 = (int) (w * p1);
        int x2 = (int) (w * p2);

        p = Math.min(p * 2, 1);
        int delta = (int) (Math.max(p * 2 - 1, 0) * h);

        for (int j = 0; j < h - delta; j++) {
            sourceRaster.getDataElements(0, j + delta, w, 1, sourceRow);

            int start = (int) (x1 * p);
            int end = (int) (w - (w - x2) * p);

            start *= (1 - (double)j / h);
            end += (w - end) * j / h;

//            System.out.printf("(%d,%d)%n", start, end);

            for (int i = 0; i < w; i++) {
                if (i >= start && i < end) {
                    int srcPos = (i - (start + end) / 2) * w / (end - start) + w / 2;
                    row[i] = sourceRow[srcPos];
                } else {
                    row[i] = 0;
                }
            }

            raster.setDataElements(0, j, w, 1, row);
        }

//        System.out.println("=========");

        return image;
    }
}
