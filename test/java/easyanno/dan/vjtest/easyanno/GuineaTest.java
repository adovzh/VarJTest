package dan.vjtest.easyanno;

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
            Assert.fail("IllegalStateException must be thrown");
        } catch (IllegalStateException e) {
            try {
                Assert.assertEquals(NotNull.class.getDeclaredMethod("value").getDefaultValue(), e.getMessage());
            } catch (NoSuchMethodException e1) {
                Assert.fail();
            }

        }
    }

    @Test
    public void notNullSpecial() {
        try {
            guinea.notNullSpecialMethod();
            Assert.fail("IllegalStateException must be thrown");
        } catch (IllegalStateException e) {
            try {
                Assert.assertEquals(
                        Guinea.class.getMethod("notNullSpecialMethod").getAnnotation(NotNull.class).value(),
                        e.getMessage());
            } catch (NoSuchMethodException e1) {
                Assert.fail();
            }
        }
    }
}
