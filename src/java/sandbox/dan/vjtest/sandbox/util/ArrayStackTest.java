package dan.vjtest.sandbox.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class ArrayStackTest {

    @Test
    public void order() {
        ArrayStack<Integer> stack = new ArrayStack<>(10);
        int[] input = RandomUtils.randomIntArray(5);

        for (int elem : input)
            stack.push(elem);

        for (int i = input.length - 1; i >= 0; i--)
            Assert.assertEquals(input[i], (long) stack.pop());
    }

    @Test
    public void underflow() {
        ArrayStack<Integer> stack = new ArrayStack<>(10);

        try {
            stack.pop();
            Assert.fail("Underflow must occur");
        } catch (IllegalStateException e) {
            Assert.assertEquals("stack underflow", e.getMessage());
        }
    }

    @Test
    public void overflow() {
        ArrayStack<Integer> stack = new ArrayStack<>(5);
        int[] input = RandomUtils.randomIntArray(10);

        for (int i = 0; i < input.length; i++) {
            try {
                stack.push(input[i]);

                if (i >= 5)
                    Assert.fail("Overflow must occur");
            } catch (IllegalStateException e) {
                if (i < 5)
                    Assert.fail("Overflow should not occur: " + i);
                else
                    Assert.assertEquals("stack overflow", e.getMessage());
            }
        }
    }
}
