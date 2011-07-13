package dan.vjtest.sandbox.index;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dovzhikov
 */
public class WordNumber {
    private static final String[] DIGITS = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};

    private int number;
    private String[] digits;

    public WordNumber(int number) {
        this.number = number;

        List<Integer> digitsList = new ArrayList<Integer>();

        while (number > 0) {
            digitsList.add(number);
            number /= 10;
        }

        this.digits = new String[digitsList.size()];

        for (int i = 0; i < digits.length; i++) {
            digits[i] = DIGITS[digitsList.get(digits.length - i - 1) % 10];
        }
    }

    public int getNumber() {
        return number;
    }

    public String getDigit(int index) {
        return digits[index];
    }

    public int getDigitCount() {
        return digits.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (i > 0)
                sb.append(' ');

            sb.append(digits[i]);
        }

        sb.append(" (").append(number).append(')');

        return sb.toString();
    }
}
