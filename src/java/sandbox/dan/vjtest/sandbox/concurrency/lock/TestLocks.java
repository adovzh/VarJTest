/*
 * Copyright (c) 2012 Alexander Dovzhikov <alexander.dovzhikov@gmail.com>.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY ALEXANDER DOVZHIKOV ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL dovzhikov OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * TestLocks.java
 *
 * Created on 07.02.2012 17:00:53
 */

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
