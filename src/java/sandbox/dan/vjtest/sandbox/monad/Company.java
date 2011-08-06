package dan.vjtest.sandbox.monad;

/**
 * @author Alexander Dovzhikov
 */
public class Company {
    private final Address address;
    private final String name;

    public Company(Address address, String name) {
        this.name = name;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
