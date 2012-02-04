package dan.vjtest.sandbox.concurrency.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Dovzhikov
 */
public class JucSharedCounter extends SharedCounterBase {
    private final Lock lock = new ReentrantLock();

    @Override
    public void lockInc() {
        lock.lock();

        try {
            super.lockInc();
        } finally {
            lock.unlock();
        }
    }
}
