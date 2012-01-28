package dan.vjtest.sandbox.util.concurrent;

import dan.vjtest.sandbox.util.Stack;

/**
 * @author Alexander Dovzhikov
 */
public interface BlockingStack<T> extends Stack<T> {
    void pushBlocking(T elem) throws InterruptedException;
    T popBlocking() throws InterruptedException;
}
