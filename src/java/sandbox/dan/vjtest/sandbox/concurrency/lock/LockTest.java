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
 * LockTest.java
 *
 * Created on 07.02.2012 17:00:44
 */

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

        System.out.printf("\"%s\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\"%n",
                spec, numThreads, duration, duration / iterations,
                (iterations * 1000000000L) / duration, counter.getValue());
    }
}
