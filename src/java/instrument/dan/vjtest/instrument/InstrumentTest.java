package dan.vjtest.instrument;

/**
 * @author Alexander Dovzhikov
 */
public class InstrumentTest {

    private final Guinea guinea;

    public InstrumentTest(int initialValue) {
        guinea = new Guinea(initialValue);
    }

    public void testGuinea() {
        guinea.add(2);
        printInfo();

        guinea.multiply(5);
        printInfo();

        guinea.increment();
        printInfo();
    }

    private void printInfo() {
        System.out.println("Value: " + guinea.getState());
    }

    public static void main(String[] args) {
        InstrumentTest instance = new InstrumentTest(10);
        instance.testGuinea();
    }
}
