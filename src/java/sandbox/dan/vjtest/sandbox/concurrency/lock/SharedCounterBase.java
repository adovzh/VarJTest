package dan.vjtest.sandbox.concurrency.lock;

/**
 * @author Alexander Dovzhikov
 */
public class SharedCounterBase implements SharedCounter {
    
    public static SharedCounter createSharedCounter(String spec) {
        if ("JVM".equalsIgnoreCase(spec))
            return new JvmSharedCounter();
        else if ("JUC".equalsIgnoreCase(spec))
            return new JucSharedCounter();
        else
            throw new IllegalArgumentException("Illegal shared counter spec: " + spec);
    }
    
    private int counter;

    @Override
    public void lockInc() {
        ++counter;
    }

    @Override
    public int getValue() {
        return counter;
    }
}
