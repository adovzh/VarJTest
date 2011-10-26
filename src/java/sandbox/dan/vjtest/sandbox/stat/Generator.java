package dan.vjtest.sandbox.stat;

import java.util.Random;

/**
 * @author Alexander Dovzhikov
 */
public class Generator implements Runnable {
    private final double lo;
    private final double hi;
    private final Consumer consumer;
    private final Random random = new Random();

    public Generator(double lo, double hi, Consumer consumer) {
        this.lo = lo;
        this.hi = hi;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            consumer.put(lo + random.nextDouble() * (hi - lo));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
