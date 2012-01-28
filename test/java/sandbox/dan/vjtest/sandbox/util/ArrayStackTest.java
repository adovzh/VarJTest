package dan.vjtest.sandbox.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class ArrayStackTest {

    protected <T> Stack<T> createStack(int capacity) {
        return new ArrayStack<>(capacity);
    }

    @Test
    public void order() {
        Stack<Integer> stack = createStack(10);
        int[] input = RandomUtils.randomIntArray(5);

        for (int elem : input)
            stack.push(elem);

        for (int i = input.length - 1; i >= 0; i--)
            Assert.assertEquals(input[i], (long) stack.pop());
    }

    @Test
    public void underflow() {
        Stack<Integer> stack = createStack(10);

        try {
            stack.pop();
            Assert.fail("Underflow must occur");
        } catch (IllegalStateException e) {
            Assert.assertEquals("underflow", e.getMessage());
        }
    }

    @Test
    public void overflow() {
        Stack<Integer> stack = createStack(5);
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
                    Assert.assertEquals("overflow", e.getMessage());
            }
        }
    }
}
