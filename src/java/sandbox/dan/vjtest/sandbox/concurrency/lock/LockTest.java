package dan.vjtest.sandbox.concurrency.lock;

import java.util.concurrent.CyclicBarrier;

/**
 * @author Alexander Dovzhikov
 */
public class LockTest {

    private final String spec;
    private final int numThreads;
    private final long iterations;

    public LockTest(String spec, int numThreads, long iterations) {
        this.spec = spec;
        this.numThreads = numThreads;
        this.iterations = iterations;
    }

    private SharedCounter runTest() {
        CyclicBarrier barrier = new CyclicBarrier(numThreads);
        Thread[] threads = new Thread[numThreads];
        SharedCounter sharedCounter = SharedCounterBase.createSharedCounter(spec);

        for (int i = 0; i < numThreads; i++)
            threads[i] = createThread(barrier, sharedCounter);

        try {
            for (Thread t : threads)
                t.join();
        } catch (InterruptedException e) {
            // ignore
        }

        return sharedCounter;
    }
    
    private Thread createThread(CyclicBarrier barrier, SharedCounter sharedCounter) {
        Runnable runner = new TestLocksRunner(barrier, sharedCounter, iterations / numThreads);
        Thread t = new Thread(runner);
        t.start();
        
        return t;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.printf("Usage: java %s [JVM | JUC] <numThreads>%n", LockTest.class.getName());
            System.exit(1);
        }

        LockTest lockTest = new LockTest(args[0], Integer.parseInt(args[1]), 500L * 1000L * 1000L);
        lockTest.runTest(); // warm up

        lockTest.measureAndReport();
    }

    private void measureAndReport() {
        long start = System.nanoTime();
        SharedCounter counter = runTest();
        long duration = System.nanoTime() - start;

        System.out.printf("%d threads, duration %,d (ns)\n", numThreads, duration);
        System.out.printf("%,d ns/op\n", duration / iterations);
        System.out.printf("%,d ops/s\n", (iterations * 1000000000L) / duration);
        System.out.println("counter = " + counter.getValue());
    }
}
