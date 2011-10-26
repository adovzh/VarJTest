package dan.vjtest.sandbox.stat;

import java.util.Random;
import java.util.concurrent.ExecutorService;

/**
 * @author Alexander Dovzhikov
 */
public class Generator {
    private final Random random = new Random();

    public Generator(final double lo, final double hi, final GeneratorListener listener, ExecutorService executor, int numTasks) {
        for (int i = 0; i < numTasks; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.interrupted()) {
                        listener.eventGenerated(lo + random.nextDouble() * (hi - lo));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            });
        }
    }
}
