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
 * MeasureUtils.java
 *
 * Created on 07.02.2012 17:18:37
 */

package dan.vjtest.sandbox.measure;

import java.util.concurrent.Callable;

/**
 * @author Alexander Dovzhikov
 */
public class MeasureUtils {

    public static long measure(Runnable r) {
        return measure(r, 1);
    }

    public static long measure(Runnable r, int repeats) {
        long start = System.currentTimeMillis();

        while (repeats-- > 0)
            r.run();

        return (System.currentTimeMillis() - start);
    }

    public static long measureNano(Runnable r, int repeats) {
        long start = System.nanoTime();

        while (repeats-- > 0)
            r.run();

        return (System.nanoTime() - start);
    }

    public static <V> V measure(Callable<V> c, long[] millis) {
        long start = System.currentTimeMillis();
        V result = null;

        try {
            result = c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        millis[0] = System.currentTimeMillis() - start;

        return result;
    }
}
