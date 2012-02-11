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
 * StringUtils.java
 *
 * Created on 07.02.2012 17:30:05
 */

package dan.vjtest.sandbox.util;

import dan.vjtest.sandbox.measure.MeasureUtils;
import dan.vjtest.sandbox.measure.RunnableUnit;
import dan.vjtest.sandbox.measure.Unit;

import java.nio.CharBuffer;

/**
 * @author Alexander Dovzhikov
 */
public class StringUtils {

    public static String questionSigns(int size) {
        // size >= 1
        char[] buffer = new char[size * 3 + 1];
        char[] chrs = {'?', ',', ' '};
        int j = 0;

        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = chrs[j++];

            if (j == 3)
                j = 0;
        }

        return new String(buffer);
    }

    public static String qSigns(int size) {
        StringBuilder sb = new StringBuilder(size * 3);
        while (--size > 0)
            sb.append("?, ");
        sb.append("?");
        return sb.toString();
    }

    public static String qbSigns(int size) {
        CharBuffer buffer = CharBuffer.allocate(size * 3 + 1);

        while (--size > 0)
            buffer.put("?, ");
        buffer.put('?');

        return new String(buffer.array());
    }

    public static void report(Unit unit, int repeats) {
        long elapsed = MeasureUtils.measure(unit, repeats);
        System.out.printf("%s(%d) - %dms elapsed%n", unit.getName(), repeats, elapsed);
    }

    public static void main(String[] args) {
        Unit questionSignsUnit = new RunnableUnit("questionsSigns") {
            public void run() {
                questionSigns(1000);
            }
        };

        Unit qSignsUnit = new RunnableUnit("qSigns") {
            public void run() {
                qSigns(1000);
            }
        };

        Unit qbSignsUnit = new RunnableUnit("qbSigns") {
            public void run() {
                qbSigns(1000);
            }
        };

        final int warmUp = 10000;
        final int repeats = 10000;

        MeasureUtils.measure(questionSignsUnit, warmUp);
        MeasureUtils.measure(qSignsUnit, warmUp);
        MeasureUtils.measure(qbSignsUnit, warmUp);

        report(questionSignsUnit, repeats);
        report(qSignsUnit, repeats);
        report(qbSignsUnit, repeats);
    }
}
