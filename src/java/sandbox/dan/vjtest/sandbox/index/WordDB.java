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
