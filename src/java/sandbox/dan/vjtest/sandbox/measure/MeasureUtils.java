package dan.vjtest.sandbox.measure;

import java.util.concurrent.Callable;

/**
 * @author Alexander Dovzhikov
 */
public class MeasureUtils {

    public static long measure(Runnable r) {
        return measure(r, 1);
    }

    public static long measure(Runnable r, int repeats) {
        long start = System.currentTimeMillis();

        while (repeats-- > 0)
            r.run();

        return (System.currentTimeMillis() - start);
    }

    public static long measureNano(Runnable r, int repeats) {
        long start = System.nanoTime();

        while (repeats-- > 0)
            r.run();

        return (System.nanoTime() - start);
    }

    public static <V> V measure(Callable<V> c, long[] millis) {
        long start = System.currentTimeMillis();
        V result = null;

        try {
            result = c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        millis[0] = System.currentTimeMillis() - start;

        return result;
    }
}
