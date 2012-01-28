package dan.vjtest.sandbox.util;

/**
 * @author Alexander Dovzhikov
 */
public class SimpleQueue<T> implements Queue<T> {
    private final T[] data;
    private int head;
    private int tail;

    public SimpleQueue(int capacity) {
        data = (T[]) new Object[capacity + 1];
        head = tail = 0;
    }

    @Override
    public void enqueue(T elem) {
        if (isFull())
            throw new IllegalStateException("queue overflow");

        putElement(elem);
    }

    @Override
    public T dequeue() {
        if (isEmpty())
            throw new IllegalStateException("queue underflow");

        return getElement();
    }

    private boolean isFull() {
        return tail + 1 == head || head == 0 && tail == data.length - 1;
    }

    private boolean isEmpty() {
        return head == tail;
    }

    private void putElement(T elem) {
        data[tail++] = elem;

        if (tail == data.length)
            tail = 0;
    }

    private T getElement() {
        T element = data[head++];

        if (head == data.length)
            head = 0;

        return element;
    }
}
