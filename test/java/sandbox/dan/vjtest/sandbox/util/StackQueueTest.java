package dan.vjtest.sandbox.util;

/**
 * @author Alexander Dovzhikov
 */
public class StackQueueTest extends QueueTest {
    @Override
    protected <T> Queue<T> createQueue(int capacity) {
        return new StackQueue<>(capacity);
    }
}
