package dan.vjtest.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author Alexander Dovzhikov
 */
public class IncFactory {

    public Inc createInc() {
        return new IncImpl();
    }

    public Inc createMeasuredInc() {
        MeasuredFilter filter = new MeasuredFilter();
        
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(IncImpl.class);
        enhancer.setCallbackFilter(filter);
        enhancer.setCallbacks(filter.getCallbacks());

        return (Inc) enhancer.create();
    }
}
