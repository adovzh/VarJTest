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
 * FundsAnalyser.java
 *
 * Created on 07.02.2012 17:11:32
 */

package dan.vjtest.sandbox.funds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

/**
 * @author Alexander Dovzhikov
 */
public class FundsAnalyser {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();

        @SuppressWarnings("unchecked")
        LinkedList<Double>[] days = new LinkedList[28];
        double lastPrice = Double.NaN;

        while ((line = in.readLine()) != null) {
//            System.out.println(line);
            int tab = line.indexOf('\t');
            if (tab != -1) {
                String dateText = line.substring(0, tab).trim();
                String moneyText = line.substring(tab + 1);

                try {
                    Date date = sdf.parse(dateText);
                    double money = Double.parseDouble(moneyText);

                    lastPrice = money;

                    cal.setTime(date);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    if (day <= 28) {
                        LinkedList<Double> prices = days[day - 1];
                        
                        if (prices == null) {
                            days[day - 1] = prices = new LinkedList<>();
                        }

                        prices.add(money);
                    }

//                    System.out.println(date + " -> " + money);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }

        double[] bought = new double[days.length];

        for (int i = 0; i < days.length; i++) {
            bought[i] = Double.NaN;
            LinkedList<Double> prices = days[i];

            if (prices != null) {
                int len = prices.size();
                double sum = 0d;
                
                for (Double price : prices)
                    sum += 1d / price;

                bought[i] = sum * lastPrice - len;
            }

            System.out.printf("%d\t%.5f%n", i + 1, bought[i]);
        }
    }
}
