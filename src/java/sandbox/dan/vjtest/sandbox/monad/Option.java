package dan.vjtest.sandbox.monad;

/**
 * @author Alexander Dovzhikov
 */
public interface Option<A> {
    A value();
    <B> Option<B> bind(Func<A,Option<B>> func);
}
