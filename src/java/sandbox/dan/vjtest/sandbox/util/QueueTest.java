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
 * QueueTest.java
 *
 * Created on 07.02.2012 17:30:05
 */

package dan.vjtest.sandbox.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public abstract class QueueTest {

    protected abstract <T> Queue<T> createQueue(int capacity);

    @Test
    public void order() {
        Queue<Integer> queue = createQueue(10);
        int[] input = RandomUtils.randomIntArray(5);

        for (int item : input)
            queue.enqueue(item);

        for (int item : input)
            Assert.assertEquals(item, (long) queue.dequeue());
    }

    @Test
    public void underflow() {
        Queue<Integer> queue = createQueue(10);

        try {
            queue.dequeue();
            Assert.fail("Underflow must occur");
        } catch (IllegalStateException e) {
            Assert.assertEquals("underflow", e.getMessage());
        }
    }

    @Test
    public void overflow() {
        Queue<Integer> queue = createQueue(5);
        int[] input = RandomUtils.randomIntArray(10);

        for (int i = 0; i < input.length; i++) {
            try {
                queue.enqueue(input[i]);

                if (i >= 5)
                    Assert.fail("Overflow must occur");
            } catch (IllegalStateException e) {
                if (i < 5)
                    Assert.fail("Overflow should not occur: " + i);
                else
                    Assert.assertEquals("overflow", e.getMessage());
            }
        }
    }
}
