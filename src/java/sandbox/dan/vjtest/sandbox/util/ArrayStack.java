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
        if (top == data.length)
            throw new IllegalStateException("stack overflow");

        data[top++] = elem;
    }

    @Override
    public T pop() {
        if (top == 0)
            throw new IllegalStateException("stack underflow");

        return data[--top];
    }
}
