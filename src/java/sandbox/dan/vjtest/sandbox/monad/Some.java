package dan.vjtest.sandbox.monad;

/**
 * @author Alexander Dovzhikov
 */
public class Some<A> implements Option<A> {
    private final A val;

    public Some(A val) {
        this.val = val;
    }

    public A value() {
        return val;
    }

    public <B> Option<B> bind(Func<A, Option<B>> func) {
        return func.eval(val);
    }
}
