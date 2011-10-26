package dan.vjtest.sandbox.stat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dovzhikov
 */
public class Buffer implements Consumer, Producer {
    private List<Double> data = new ArrayList<>();

    @Override
    public synchronized void put(double val) {
        data.add(val);
    }

    @Override
    public List<Double> get() {
        List<Double> anotherList = new ArrayList<>();
        List<Double> result;
        
        synchronized (this) {
            result = data;
            data = anotherList;
        }

        return result;
    }
}
