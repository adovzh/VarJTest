package dan.vjtest.sandbox.util.concurrent;

import dan.vjtest.sandbox.util.ArrayStack;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Dovzhikov
 */
public class ArrayBlockingStack<T> extends ArrayStack<T> implements BlockingStack<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public ArrayBlockingStack(int capacity) {
        super(capacity);
    }

    @Override
    public void pushBlocking(T elem) throws InterruptedException {
        lock.lock();

        try {
            while (isFull())
                notFull.await();

            push(elem);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T popBlocking() throws InterruptedException {
        lock.lock();
        
        try {
            while (isEmpty())
                notEmpty.await();
            
            T elem = pop();
            notFull.signalAll();

            return elem;
        } finally {
            lock.unlock();
        }
    }
}
