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
 * PeriodicRunner.java
 *
 * Created on 19.12.2011 14:01:14
 */

package dan.vjtest.sandbox.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alexander Dovzhikov
 */
public final class PeriodicRunner implements Runnable {
    private static final int STATE_STOPPED  = 0;
    private static final int STATE_STARTING = 1;
    private static final int STATE_RUNNING  = 2;
    private static final int STATE_STOPPING = 3;

    private final Runnable task;
    private final long period;
    
    private volatile Thread thread;
    private final AtomicInteger state = new AtomicInteger(STATE_STOPPED);

    public PeriodicRunner(Runnable task, long period) {
        this.period = period;
        this.task = task;
    }

    public boolean start() {
        boolean success;

        if (success = state.compareAndSet(STATE_STOPPED, STATE_STARTING)) {
            thread = new Thread(this);
            thread.start();
        }

        return success;
    }

    public boolean stop() {
        return state.compareAndSet(STATE_RUNNING, STATE_STOPPING);
    }

    public boolean isRunning() {
        return state.get() == STATE_RUNNING && !thread.isInterrupted();
    }

    @Override
    public void run() {
        if (Thread.currentThread() != thread)
            return;

        state.set(STATE_RUNNING);

        try {
            while (isRunning()) {
                task.run();

                try {
                    Thread.sleep(period);
                } catch (InterruptedException e) {
                    stop();
                }
            }
        } finally {
            state.set(STATE_STOPPED);
        }
    }
}
