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
 * BoundedStackTest.java
 *
 * Created on 24.01.2012 16:06:25
 */

package dan.vjtest.sandbox.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alexander Dovzhikov
 */
public class BoundedStackTest {
	public static void main(String[] args) {
		final BoundedStack stack = new BoundedStack(5);
		final AtomicInteger counter = new AtomicInteger();
		
		Runnable producer1 = new Producer(stack, counter, "P1");
		Runnable producer2 = new Producer(stack, counter, "P2");
		
		Runnable consumer = new Consumer(stack);
		
		Thread producer1Thread = new Thread(producer1);
		Thread producer2Thread = new Thread(producer2);
		Thread consumerThread = new Thread(consumer);
		
		producer1Thread.start();
		producer2Thread.start();

		hang(2500L);
		consumerThread.start();
		hang(2500L);

		producer1Thread.interrupt();
		producer2Thread.interrupt();
		consumerThread.interrupt();
	}

	private static void hang(long period) {
		try {
			Thread.sleep(period);
		} catch (InterruptedException e) {
			// ignore
		}
	}

	private static class Producer implements Runnable {
		private final BoundedStack stack;
		private final AtomicInteger counter;
		private final String name;

		public Producer(BoundedStack stack, AtomicInteger counter, String name) {
			this.stack = stack;
			this.counter = counter;
			this.name = name;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 5; i++) {
					int value = counter.incrementAndGet();
					System.out.println("["+ name + "] Pushing: " + value);
					stack.push(value);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static class Consumer implements Runnable {
		private final BoundedStack stack;

		public Consumer(BoundedStack stack) {
			this.stack = stack;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 20; i++) {
					System.out.println("Consumed: " + stack.pop());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
