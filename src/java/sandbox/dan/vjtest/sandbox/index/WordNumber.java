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
 * WordNumber.java
 *
 * Created on 07.02.2012 17:12:06
 */

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
