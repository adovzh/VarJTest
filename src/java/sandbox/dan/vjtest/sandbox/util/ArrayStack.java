package dan.vjtest.sandbox.util;

/**
 * @author Alexander Dovzhikov
 */
public class ArrayStack<T> implements Stack<T> {
    private final T[] data;
    private int top;

    public ArrayStack(int capacity) {
        data = (T[]) new Object[capacity];
        top = 0;
    }

    @Override
    public void push(T elem) {
        if (isFull())
            throw new IllegalStateException("overflow");

        data[top++] = elem;
    }

    @Override
    public T pop() {
        if (isEmpty())
            throw new IllegalStateException("underflow");

        return data[--top];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public boolean isFull() {
        return top == data.length;
    }
}
