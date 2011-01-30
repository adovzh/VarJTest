package dan.vjtest.cglib.test;

import dan.vjtest.cglib.Inc;
import dan.vjtest.cglib.IncFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Alexander Dovzhikov
 */
public class CGLibIncTest {
    private IncFactory factory;

    @Before
    public void init() {
        factory = new IncFactory();
    }

    @Test
    public void ordinary() {
        Inc ordinary = factory.createInc();
        anyInc(ordinary);
    }

    @Test
    public void measured() {
        Inc measured = factory.createMeasuredInc();
        anyInc(measured);
    }

    private void anyInc(Inc inc) {
        assertNotNull(inc);
        assertEquals(1, inc.inc());
    }

    @Test
    public void multiple() {
        Inc measured  = factory.createMeasuredInc();
        int step = 1000000;

        assertNotNull(measured);
        assertEquals(step, measured.incMultiple(step));
    }
}
