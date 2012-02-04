package dan.vjtest.sandbox.concurrency.lock;

/**
 * @author Alexander Dovzhikov
 */
public interface SharedCounter {
    void lockInc();
    int getValue();
}
