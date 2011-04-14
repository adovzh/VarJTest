package dan.vjtest.sandbox.measure;

/**
 * @author Alexander Dovzhikov
 */
public class MeasureUtils {

    public static long measure(Runnable r, int repeats) {
        long start = System.currentTimeMillis();

        while (--repeats > 0)
            r.run();

        return (System.currentTimeMillis() - start);
    }

    public static long measureNano(Runnable r, int repeats) {
        long start = System.nanoTime();

        while (--repeats > 0)
            r.run();

        return (System.nanoTime() - start);
    }
}
