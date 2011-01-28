package dan.vjtest.cglib;

/**
 * @author Alexander Dovzhikov
 */
public class IncImpl implements Inc {
    private int value = 0;

    public int inc() {
        return ++value;
    }

    @Measured
    public int incMultiple(int num) {
        if (num <= 0)
            throw new IllegalArgumentException("num must ne positive");

        while (--num > 0)
            inc();

        return inc();
    }
}
