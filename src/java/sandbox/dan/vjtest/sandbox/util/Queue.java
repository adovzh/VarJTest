package dan.vjtest.sandbox.util;

/**
 * @author Alexander Dovzhikov
 */
public interface Queue<T> {
    void enqueue(T elem);
    T dequeue();
}
