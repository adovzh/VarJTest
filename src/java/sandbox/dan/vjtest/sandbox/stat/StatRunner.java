package dan.vjtest.sandbox.stat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author Alexander Dovzhikov
 */
public class StatRunner {
    private static final Logger log = LoggerFactory.getLogger(StatRunner.class);

    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        ExecutorService producers = Executors.newFixedThreadPool(1);
        ScheduledExecutorService consumers = Executors.newScheduledThreadPool(1);

        producers.execute(new Generator(2, 20, buffer));
        consumers.scheduleAtFixedRate(new Processor(buffer), 1, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.debug("Shutting producers down");
            producers.shutdownNow();
            log.debug("Shutting consumers down");
            consumers.shutdown();
            log.debug("OK");
        }
    }
}
