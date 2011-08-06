package dan.vjtest.sandbox.monad;

/**
 * @author Alexander Dovzhikov
 */
public class Options {
    public static <A> Option<A> maybe(A a) {
        return (a != null) ? new Some<A>(a) : new None<A>();
    }

    public static <A,B> Func<A,Option<B>> maybeFunc(final Func<A,B> func) {
        return new Func<A, Option<B>>() {
            public Option<B> eval(A a) {
                return maybe(func.eval(a));
            }
        };
    }

    public static <A,B> B chainMaybe(A base, Func<?,?> ... functions) {
        Option<?> mBase = maybe(base);

        for (Func f : functions) {
            mBase = mBase.bind(maybeFunc(f));
        }

        return (B) mBase.value();
    }
}
