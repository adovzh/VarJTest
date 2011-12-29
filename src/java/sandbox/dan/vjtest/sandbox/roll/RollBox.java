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
 * RollBox.java
 *
 * Created on 28.12.2011 13:24:57
 */

package dan.vjtest.sandbox.roll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @author Alexander Dovzhikov
 */
public class RollBox {
	private final LinkedList<RollSymbol> symbols = new LinkedList<>();
	private final Queue<Character> characterPool = new LinkedList<>();

	private final ThreadGroup rollBoxGroup;

	public RollBox() {
		rollBoxGroup = new ThreadGroup("RollBoxGroup");
		
		for (char c = 'A'; c <= 'Z'; c++)
			characterPool.add(c);
	}

	public void setUp() {
		synchronized (symbols) {
			for (int i = 0; i < 18; i++)
				symbols.add(new RollSymbol(characterPool.remove()));

			symbols.notifyAll();
		}
	}

	public void startRoll() {
		Thread rollThread = new Thread(rollBoxGroup, "Roll-thread") {
			@Override
			public void run() {
				boolean running = true;

				while (running) {
					synchronized (symbols) {
						System.out.println("Rolling " + symbols.size() + " symbols");

						long time = System.currentTimeMillis();
						long nextWakeUp = Long.MAX_VALUE;

						for (RollSymbol symbol : symbols) {
							long symbolTime = symbol.doRoll(time);

							if (symbolTime < nextWakeUp)
								nextWakeUp = symbolTime;
						}

						long timeout = nextWakeUp - time;
						if (timeout > 0) {
							System.out.println("Sleep for " + timeout + " ms");
							try {
								symbols.wait(timeout);
							} catch (InterruptedException e) {
								running = false;
							}
						}
					}
				}
			}
		};

		rollThread.start();
	}
	
	public void startStrategyWatcher(final String fileName) {

		new Thread(rollBoxGroup, "Strategy-Watcher") {
			private File file = new File(fileName);
			private long lastModified;

			@Override
			public void run() {
				boolean running = true;
				
				while (running) {
					long modified = (file.exists() && file.isFile()) ? file.lastModified() : 0L;

					if (modified != lastModified) {
						lastModified = modified;

						if (modified > 0) {
							// do the job
							try {
								FileReader in = new FileReader(file);

								try {
									int c = in.read();
									c = (c - 'A') % 60;
									System.out.println("Second: " + c);
									long time = System.currentTimeMillis();

									synchronized (symbols) {
										int i = 0;
										for (RollSymbol symbol : symbols) {
											symbol.setWakeUpProvider(new SecondWakeUpProvider(10 * i++), time);
										}

										symbols.notifyAll();
									}
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									try {
										in.close();
									} catch (IOException e) {
										// ignore
									}
								}
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
						}
					}

					try {
						sleep(1000L);
					} catch (InterruptedException e) {
						running = false;
					}
				}
			}
		}.start();
	}

	public void startShuffler() {
		new Thread(rollBoxGroup, "Shuffler") {
			private boolean dir = true;
			private Random random = new Random();

			@Override
			public void run() {
				boolean running = true;
				
				while (running) {
					if (dir) {
						long time = System.currentTimeMillis();

						synchronized (symbols) {
							int pos = random.nextInt(symbols.size() + 1);
							RollSymbol symbol = new RollSymbol(characterPool.remove());
							symbol.setWakeUpProvider(new SecondWakeUpProvider(10 * pos), time);
							symbols.add(pos, symbol);

							System.out.println("Symbol " + symbol.getName() + " added");
						}
					} else {
						synchronized (symbols) {
							int pos = random.nextInt(symbols.size());
							char c = symbols.remove(pos).getName();
							characterPool.add(c);

							System.out.println("Symbol " + c + " removed");
						}
					}

					dir = !dir;

					try {
						sleep(17000L);
					} catch (InterruptedException e) {
						running = false;
					}
				}

				System.out.println("Shuffler stopped");
			}
		}.start();
	}
	
	public void joinThreads() {
		int count;

		while ((count = rollBoxGroup.activeCount()) > 0) {
			System.out.println("Threads left: " + count);
			rollBoxGroup.interrupt();
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}
}
