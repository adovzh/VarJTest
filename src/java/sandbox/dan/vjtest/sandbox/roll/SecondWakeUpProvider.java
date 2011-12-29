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
 * SecondWakeUpProvider.java
 *
 * Created on 28.12.2011 13:39:13
 */

package dan.vjtest.sandbox.roll;

import java.util.Calendar;

/**
 * @author Alexander Dovzhikov
 */
public class SecondWakeUpProvider implements WakeUpProvider {
	private final int second;

	public SecondWakeUpProvider(int second) {
		this.second = second % 60;
	}

	@Override
	public long getNextWakeUp(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.SECOND, second);

		while (cal.getTimeInMillis() <= time)
			cal.add(Calendar.MINUTE, 1);

		return cal.getTimeInMillis();
	}
}