package dan.vjtest.sandbox.swing.genie;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * @author Alexander Dovzhikov
 */
public class Genie {
//    private final int x1;
//    private final int x2;
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

        for (int j = 0; j < h; j++) {
            sourceRaster.getDataElements(0, j, w, 1, sourceRow);

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
