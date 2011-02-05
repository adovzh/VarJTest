package dan.vjtest.sandbox.annotations;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class GuineaTest {
    private Guinea guinea = new Guinea();

    @Test
    public void greet() {
        guinea.greetMethod();
    }

    @Test
    public void bye() {
        guinea.byeMethod();
    }

    @Test
    public void anotherGreet() {
        guinea.anotherGreetMethod();
    }

    @Test
    public void notNull() {
        Object result = guinea.notNullMethod(1);
        Assert.assertNotNull(result);

        try {
            guinea.notNullMethod(2);
            Assert.fail();
        } catch (IllegalStateException e) {
            // ok
        }
    }
}
