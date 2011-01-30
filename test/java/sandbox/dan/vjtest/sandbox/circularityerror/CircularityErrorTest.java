package dan.vjtest.sandbox.circularityerror;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class CircularityErrorTest {

    @Test
    public void simpleCircularity() {
        Derived derived = new Derived();
        derived.baseValue = 1;
        derived.derivedValue = 2;

        Assert.assertEquals(1, derived.baseValue);
        Assert.assertEquals(2, derived.derivedValue);
    }
}
