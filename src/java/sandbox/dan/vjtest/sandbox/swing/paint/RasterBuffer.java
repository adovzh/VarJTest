package dan.vjtest.sandbox.swing.paint;

import java.awt.image.*;

/**
* @author Alexander Dovzhikov
*/
class RasterBuffer {
    private final int[] data;
    private final WritableRaster raster;

    public RasterBuffer(ColorModel colorModel, int w, int h) {
        this.data = new int[w * h];
        this.raster = Raster.createWritableRaster(
                new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, w, h, ((PackedColorModel) colorModel).getMasks()),
                new DataBufferInt(data, data.length), null);
    }

    public int getLength() {
        return data.length;
    }

    public int[] getData() {
        return data;
    }

    public WritableRaster getRaster() {
        return raster;
    }
}
