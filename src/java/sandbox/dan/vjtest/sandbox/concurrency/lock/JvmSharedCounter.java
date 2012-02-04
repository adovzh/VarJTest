package dan.vjtest.sandbox.concurrency.lock;

/**
 * @author Alexander Dovzhikov
 */
public class JvmSharedCounter extends SharedCounterBase {
    @Override
    public synchronized void lockInc() {
        super.lockInc();
    }
}
