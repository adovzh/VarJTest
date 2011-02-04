package dan.vjtest.sandbox.annotations.temp.greet;

/**
 * @author Alexander Dovzhikov
 */
public class Guinea {

    public static void main(String[] args) {
        Guinea g = new Guinea();

        g.method1();
        g.method2();
        g.method3();
    }

    @Greet
    public void method1() {
        System.out.println("method1");
    }

    @Bye
    public void method2() {
        System.out.println("method2");
    }

    @Greet
    public void method3() {
        System.out.println("method3");
    }
}
