package dan.vjtest.cglib;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * @author Alexander Dovzhikov
 */
public class MeasuredFilter extends CallbackHelper {

    private static final Callback MEASURED_CALLBACK = new MethodInterceptor() {
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("Method: " + method.getName());
            long start = System.currentTimeMillis();
            Object result = proxy.invokeSuper(obj, args);
            System.out.println("Elapsed: " + (System.currentTimeMillis() - start) + "ms");

            return result;
        }
    };

    public MeasuredFilter() {
        super(IncImpl.class, new Class[] {Inc.class});
    }

    @Override
    protected Object getCallback(Method method) {
        return (method.isAnnotationPresent(Measured.class)) ? MEASURED_CALLBACK : NoOp.INSTANCE;
    }
}
