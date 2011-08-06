package dan.vjtest.sandbox.monad;

/**
 * @author Alexander Dovzhikov
 */
public class None<A> implements Option<A> {
    public A value() {
        return null;
    }

    public <B> Option<B> bind(Func<A, Option<B>> aOptionFunc) {
        return new None<B>();
    }
}
