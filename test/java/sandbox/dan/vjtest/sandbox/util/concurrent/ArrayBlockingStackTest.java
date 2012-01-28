package dan.vjtest.sandbox.util.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dovzhikov
 */
public class ArrayBlockingStackTest {
    @Test
    public void justTest() {
        final BlockingStack<Integer> stack = new ArrayBlockingStack<>(5);
        Writer w1 = new Writer(stack, 1, 10);
        Writer w2 = new Writer(stack, 11, 10);
        Reader r = new Reader(stack);
        
        w1.start();
        w2.start();
        r.start();

        try {
            w1.join();
            w2.join();
            r.join();
        } catch (InterruptedException e) {
            // ignore
        }
    }

    static class Writer extends Thread {
        static Logger log = LoggerFactory.getLogger(Writer.class);
        BlockingStack<Integer> stack;
        int start;
        int length;
        String name;
        static int count;

        Writer(BlockingStack<Integer> stack, int start, int length) {
            this.stack = stack;
            this.start = start;
            this.length = length;
            name = "Writer-" + (++count);
        }

        @Override
        public void run() {
            try {
                for (int i = start; i < start + length; i++)
                    stack.pushBlocking(i);
                
                log.debug(name + " finished");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Reader extends Thread {
        static Logger log = LoggerFactory.getLogger(Reader.class);
        final BlockingStack<Integer> stack;
        final List<Integer> buffer = new ArrayList<>();


        Reader(BlockingStack<Integer> stack) {
            this.stack = stack;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000L);
                
                for (int i = 0; i < 20; i++)
                    buffer.add(stack.popBlocking());

                log.debug(buffer.toString());
            } catch (InterruptedException e) {
                log.info("Reader interrupted");
            }
        }
    }
}
