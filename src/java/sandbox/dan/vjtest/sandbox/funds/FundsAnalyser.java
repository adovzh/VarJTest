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
