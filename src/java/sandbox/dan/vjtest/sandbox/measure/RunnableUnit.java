package dan.vjtest.sandbox.measure;

/**
 * @author Alexander Dovzhikov
 */
public abstract class RunnableUnit implements Unit {

    private final String name;

    public RunnableUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
