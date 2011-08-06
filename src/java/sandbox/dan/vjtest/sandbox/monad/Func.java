package dan.vjtest.sandbox.monad;

/**
 * @author Alexander Dovzhikov
 */
public interface Func<A,B> {
    B eval(A a);
}
