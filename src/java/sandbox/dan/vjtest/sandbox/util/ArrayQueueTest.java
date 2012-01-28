package dan.vjtest.sandbox.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class ArrayQueueTest {

    @Test
    public void order() {
        ArrayQueue<Integer> queue = new ArrayQueue<>(10);
        int[] input = RandomUtils.randomIntArray(5);

        for (int item : input)
            queue.enqueue(item);

        for (int item : input)
            Assert.assertEquals(item, (long) queue.dequeue());
    }
    
    @Test
    public void underflow() {
        ArrayQueue<Integer> queue = new ArrayQueue<>(10);
        
        try {
            queue.dequeue();
            Assert.fail("Underflow must occur");
        } catch (IllegalStateException e) {
            Assert.assertEquals("queue underflow", e.getMessage());
        }
    }
    
    @Test
    public void overflow() {
        ArrayQueue<Integer> queue = new ArrayQueue<>(5);
        int[] input = RandomUtils.randomIntArray(10);
        
        for (int i = 0; i < input.length; i++) {
            try {
                queue.enqueue(input[i]);
                
                if (i >= 5)
                    Assert.fail("Overflow must occur");
            } catch (IllegalStateException e) {
                if (i < 5)
                    Assert.fail("Overflow should not occur: " + i);
                else
                    Assert.assertEquals("queue overflow", e.getMessage());
            }
        }
    }
}
