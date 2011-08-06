package dan.vjtest.sandbox.monad;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class MaybeTest {
    private final Func<Company,Address> mAddress = new Func<Company, Address>() {
        public Address eval(Company company) {
            return company.getAddress();
        }
    };

    private final Func<Company,String> mName = new Func<Company, String>() {
        public String eval(Company company) {
            return company.getName();
        }
    };

    private final Func<Address,String> mStreet = new Func<Address, String>() {
        public String eval(Address address) {
            return address.getStreet();
        }
    };

    @Test
    public void someTest() {
        Company company = new Company(new Address("Sesame"), "Comp1");
        Assert.assertEquals("Sesame", Options.chainMaybe(company, mAddress, mStreet));
        Assert.assertEquals("Comp1", Options.chainMaybe(company, mName));
    }

    @Test
    public void someNone() {
        Company company = new Company(null, "Comp2");
        Assert.assertNull(Options.chainMaybe(company, mAddress, mStreet));
        Assert.assertEquals("Comp2", Options.chainMaybe(company, mName));
        Assert.assertNull(Options.chainMaybe(null, mName));
    }
}
