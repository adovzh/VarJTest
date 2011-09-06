package dan.vjtest.sandbox.kabutz.symposium;

public class Test01Deadlock {
    public static void main(String[] args) throws InterruptedException {
        Symposium symposium = new Symposium(5);
        symposium.run();
    }
}
