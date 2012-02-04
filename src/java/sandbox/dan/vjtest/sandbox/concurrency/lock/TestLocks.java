package dan.vjtest.sandbox.concurrency.lock;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Dovzhikov
 */
public class TestLocks implements Runnable {
    public enum LockType { JVM, JUC }
    public static LockType lockType;

    public static final long ITERATIONS = 500L * 1000L *1000L;
    public static long counter = 0L;

    public static final Object jvmLock = new Object();
    public static final Lock jucLock = new ReentrantLock();
    private static int numThreads;
    private static CyclicBarrier barrier;

    public static void main(final String[] args) throws Exception
    {
        lockType = LockType.valueOf(args[0]);
        numThreads = Integer.parseInt(args[1]);
        
        runTest(numThreads); // warm up
        counter = 0L;

        final long start = System.nanoTime();
        runTest(numThreads);
        final long duration = System.nanoTime() - start;

        System.out.printf("%d threads, duration %,d (ns)\n", numThreads, duration);
        System.out.printf("%,d ns/op\n", duration / ITERATIONS);
        System.out.printf("%,d ops/s\n", (ITERATIONS * 1000000000L) / duration);
        System.out.println("counter = " + counter);
    }

    private static void runTest(final int numThreads) throws Exception
    {
        barrier = new CyclicBarrier(numThreads);
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new TestLocks());
        }

        for (Thread t : threads)
        {
            t.start();
        }

        for (Thread t : threads)
        {
            t.join();
        }
    }

    public void run()
    {
        try
        {
            barrier.await();
        }
        catch (Exception e)
        {
            // don't care
        }

        switch (lockType)
        {
            case JVM: jvmLockInc(); break;
            case JUC: jucLockInc(); break;
        }
    }

    private void jvmLockInc()
    {
        long count = ITERATIONS / numThreads;
        while (0 != count--)
        {
            synchronized (jvmLock)
            {
                ++counter;
            }
        }
    }

    private void jucLockInc()
    {
        long count = ITERATIONS / numThreads;
        while (0 != count--)
        {
            jucLock.lock();
            try
            {
                ++counter;
            }
            finally
            {
                jucLock.unlock();
            }
        }
    }
}
