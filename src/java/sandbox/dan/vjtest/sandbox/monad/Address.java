package dan.vjtest.sandbox.monad;

/**
 * @author Alexander Dovzhikov
 */
public class Address {
    private final String street;

    public Address(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }
}
