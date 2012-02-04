package dan.vjtest.sandbox.concurrency.lock;

import java.util.concurrent.CyclicBarrier;

/**
 * @author Alexander Dovzhikov
 */
public class TestLocksRunner implements Runnable {
    private final CyclicBarrier barrier;
    private final SharedCounter counter;
    private final long numIterations;

    public TestLocksRunner(CyclicBarrier barrier, SharedCounter counter, long numIterations) {
        this.barrier = barrier;
        this.counter = counter;
        this.numIterations = numIterations;
    }

    @Override
    public void run() {
        try {
            barrier.await();
        } catch (Exception e) {
            // ignore
        }

        for (long i = numIterations; i > 0; i--)
            counter.lockInc();
    }
}
