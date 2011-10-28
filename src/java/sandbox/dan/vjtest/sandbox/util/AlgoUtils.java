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
 * AlgoUtils.java
 *
 * Created on 28.10.2011 18:06:32
 */

package dan.vjtest.sandbox.util;

/**
 * @author Alexander Dovzhikov
 */
public class AlgoUtils {

    private AlgoUtils() {
    }

    public static long select(long[] data, int from, int to, int k) {
        while (true) {
            // not optimal initial pivot selection
            int pivot = partition(data, from, to, (from + to) >> 1);
            int diff = pivot - from + 1;

            if (diff == k) {
                return data[pivot];
            } else if (k < diff) {
                to = pivot - 1;
            } else {
                k -= diff;
                from = pivot + 1;
            }
        }
    }

    private static int partition(long[] data, int left, int right, int pivotIndex) {
        long pivotValue = data[pivotIndex];
        swap(data, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (data[i] < pivotValue) {
                swap(data, storeIndex++, i);
            }
        }
        swap(data, storeIndex, right);
        return storeIndex;
    }

    private static void swap(long[] data, int i, int j) {
        if (i != j) {
            long temp = data[i];
            data[i] = data[j];
            data[j] = temp;
        }
    }
}
