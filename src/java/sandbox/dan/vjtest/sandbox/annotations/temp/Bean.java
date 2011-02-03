package dan.vjtest.sandbox.annotations.temp;

/**
 * @author Alexander Dovzhikov
 */
public class Bean {
    private int f;

    public int getF() {
        return f;
    }

    public void setF(int f) {
        if (f >= 0) {
            this.f = f;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
