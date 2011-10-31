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
 * AlgoUtilsTest.java
 *
 * Created on 28.10.2011 18:26:46
 */

package dan.vjtest.sandbox.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Alexander Dovzhikov
 */
public class AlgoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(AlgoUtilsTest.class);

    private Random random = new Random();

    @Test
    public void testMinimum() {
        long[] data = new long[200000];

        int numTests = 100;
        for (int round = 1; round <= numTests; round++) {
            for (int i = 0; i < data.length; i++)
                data[i] = random.nextLong() % 200;

            int k = random.nextInt(data.length);
            long min = AlgoUtils.select(data, 0, data.length - 1, k + 1);

            Arrays.sort(data);
            Assert.assertEquals("Round " + round, data[k], min);
        }
    }
    
    @Test
    public void testAll() {
        int numTests = 1000;
        
        for (int round = 1; round <= numTests; round++) {
            // prepare data
            long[] data = new long[(round - 1) * 10 + 1];

            for (int i = 0; i < data.length; i++) {
                data[i] = random.nextLong() % 1000;
            }
            
            // prepare sorted copy
            long[] sortedCopy = copy(data);
            Arrays.sort(sortedCopy);

            for (int k = 1; k <= data.length; k++) {
                // test kth order statistic
                long[] dataToAnalyze = copy(data);
                long stat = AlgoUtils.select(dataToAnalyze, 0, dataToAnalyze.length - 1, k);
                Assert.assertEquals("Round " + round + ", k = " + k, sortedCopy[k - 1], stat);
            }

            log.debug("Round {} finished", round);
        }
    }
    
    private static long[] copy(long[] origin) {
        long[] arr = new long[origin.length];
        System.arraycopy(origin, 0, arr, 0, origin.length);
        return arr;
    }
}
