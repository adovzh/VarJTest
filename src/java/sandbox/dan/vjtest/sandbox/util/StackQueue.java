package dan.vjtest.sandbox.util;

/**
 * @author Alexander Dovzhikov
 */
public class StackQueue<T> implements Queue<T> {
    private final Stack<T> inStack;
    private final Stack<T> outStack;

    public StackQueue(int capacity) {
        inStack = new ArrayStack<>(capacity);
        outStack = new ArrayStack<>(capacity);
    }

    @Override
    public void enqueue(T elem) {
        inStack.push(elem);
    }

    @Override
    public T dequeue() {
        if (outStack.isEmpty())
            while (!inStack.isEmpty())
                outStack.push(inStack.pop());

        return outStack.pop();
    }
}
