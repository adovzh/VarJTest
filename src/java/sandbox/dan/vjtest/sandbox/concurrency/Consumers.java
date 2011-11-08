/*
 * Copyright (c) 2011 Alexander Dovzhikov <alexander.dovzhikov@gmail.com>.
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
 * Consumers.java
 *
 * Created on 08.11.2011 15:55:48
 */

package dan.vjtest.sandbox.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Alexander Dovzhikov
 */
public class Consumers implements Runnable {
    private final int numConsumers;
    private final DataProducer producer;
    private final CountDownLatch startLatch;
    private final CountDownLatch endLatch;
    private final AtomicLong counter = new AtomicLong();

    public Consumers(int numConsumers, DataProducer producer) {
        this.numConsumers = numConsumers;
        this.producer = producer;
        this.startLatch = new CountDownLatch(1);
        this.endLatch = new CountDownLatch(numConsumers);

        for (int i = 0; i < numConsumers; i++)
            new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            startLatch.await();
        } catch (InterruptedException e) {
            return;
        }

        boolean running = true;

        while (running) {
            synchronized (producer) {
                if (running = producer.hasMoreElements()) {
                    producer.nextElement();
                    counter.incrementAndGet();
                }
            }
        }

        endLatch.countDown();
    }

    public void go() {
        startLatch.countDown();
        long startTime = System.nanoTime();

        try {
            endLatch.await();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        System.out.printf("%d,%d%n", numConsumers, (System.nanoTime() - startTime));
    }
}
