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
 * WordDB.java
 *
 * Created on 07.02.2012 17:12:06
 */

package dan.vjtest.sandbox.index;

import dan.vjtest.sandbox.measure.MeasureUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

/**
 * @author Alexander Dovzhikov
 */
public class WordDB {
    private WordNumber[] data;
    private Map<String, List<WordNumber>> index;

    public WordDB(int numRecords) {
        data = new WordNumber[numRecords];

        for (int i = 0; i < numRecords; i++) {
            data[i] = new WordNumber(i + 1);
        }

        buildIndex();
    }

    private void buildIndex() {
        index = new TreeMap<String, List<WordNumber>>();

        for (WordNumber wn : data) {
            String column = wn.getDigit(0);

            if (!index.containsKey(column)) {
                index.put(column, new ArrayList<WordNumber>());
            }

            index.get(column).add(wn);
        }
    }

    public List<WordNumber> fullScanFirstColumn(String wordDigit) {
        List<WordNumber> wns = new ArrayList<WordNumber>();

        for (WordNumber wn : data) {
            if (wn.getDigitCount() > 0 && wn.getDigit(0).equals(wordDigit)) {
                wns.add(wn);
            }
        }

        return wns;
    }

    public List<WordNumber> indexedFirstColumn(String wordDigit) {
        List<WordNumber> wordNumbers = index.get(wordDigit);
        return (wordNumbers != null) ? new ArrayList<WordNumber>(wordNumbers) : new ArrayList<WordNumber>();
    }

    public static void main(String[] args) {
        WordNumber wn = new WordNumber(135);
        System.out.println("num: " + wn);

        final WordDB db = new WordDB(5000000);
        long[] millis = { 0L };

        Callable<List<WordNumber>> fullScan = new Callable<List<WordNumber>>() {
            public List<WordNumber> call() throws Exception {
                return db.fullScanFirstColumn("Two");
            }
        };

        Callable<List<WordNumber>> indexed = new Callable<List<WordNumber>>() {
            public List<WordNumber> call() throws Exception {
                return db.indexedFirstColumn("Two");
            }
        };

        List<WordNumber> rs = MeasureUtils.measure(fullScan, millis);
        System.out.println("Full scan: " + millis[0] + " ms");

        List<WordNumber> rs2 = MeasureUtils.measure(indexed, millis);
        System.out.println("Indexed scan: " + millis[0] + " ms");

        System.out.println("Full scan result size: " + rs.size());
        System.out.println("Indexed result size: " + rs2.size());
    }
}
