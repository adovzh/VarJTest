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
