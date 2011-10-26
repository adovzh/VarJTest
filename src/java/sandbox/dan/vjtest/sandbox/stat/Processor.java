package dan.vjtest.sandbox.stat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Alexander Dovzhikov
 */
public class Processor implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Processor.class);

    private final Producer producer;

    public Processor(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        List<Double> data = producer.get();
        
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double sum = 0;
        double sum2 = 0;
        int count = 0;

        for (double item : data) {
            count++;
            sum += item;
            sum2 += item * item;

            if (item < min)
                min = item;

            if (item > max)
                max = item;
        }
        
        sum  /= count;
        sum2 /= count;
        
        double stddev = Math.sqrt(sum2 - sum * sum);

        log.info(String.format("Min/avg/max/stddev: %.3f/%.3f/%.3f/%.3f", min, sum, max, stddev));
    }
}
