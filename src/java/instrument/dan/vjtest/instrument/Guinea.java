package dan.vjtest.instrument;

/**
 * @author Alexander Dovzhikov
 */
public class Guinea {
    private int state;

    public Guinea(int state) {
        this.state = state;
    }

    public void increment() {
        state++;
    }

    public void add(int value) {
        state += value;
    }

    public void multiply(int value) {
        state *= value;
    }

    public int getState() {
        return state;
    }
}
