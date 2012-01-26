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
 * Deadlock.java
 *
 * Created on 26.01.2012 14:30:28
 */

package dan.vjtest.sandbox.concurrency;

import java.util.concurrent.CyclicBarrier;

/**
 * Simplest program that always deadlocks.
 *
 * @author Alexander Dovzhikov
 */
public class Deadlock {
	private final CyclicBarrier barrier;

	public Deadlock(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	public synchronized void deadlock(Deadlock partner){
		try {
			barrier.await();
			partner.deadlock(this);
		} catch (Exception e) {
			// ignore
		}
		System.out.println("Unreachable statement");
	}

	static class DeadlockRunner implements Runnable {
		private final Deadlock d1;
		private final Deadlock d2;

		DeadlockRunner(Deadlock d1, Deadlock d2) {
			this.d1 = d1;
			this.d2 = d2;
		}

		@Override
		public void run() {
			d1.deadlock(d2);
		}
	}

	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(2);
		Deadlock d1 = new Deadlock(barrier);
		Deadlock d2 = new Deadlock(barrier);

		// start threads
		new Thread(new DeadlockRunner(d1, d2), "T-1").start();
		new Thread(new DeadlockRunner(d2, d1), "T-2").start();

	}
}
