package dan.vjtest.sandbox.util;

/**
 * @author Alexander Dovzhikov
 */
public class ArrayQueueTest extends QueueTest {

    @Override
    protected <T> Queue<T> createQueue(int capacity) {
        return new ArrayQueue<>(capacity);
    }

}
