package dan.vjtest.sandbox.annotations.temp.greet;

/**
 * @author Alexander Dovzhikov
 */
public class Guinea {

    public static void main(String[] args) throws InterruptedException {
        Guinea g = new Guinea();

        try {
            g.method1();
            g.method2();
            g.method3();
            g.method4();
            g.method5();
        } catch (Exception e) {
            Thread.sleep(500L);
            e.printStackTrace();
        }
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

    @NotNull
    public Object method4() {
        Object var = null;
        int code = 1;
        System.out.println("method4");

        if (var == null) {
            var = (code == 1) ? new Object() : null;
        }

        return var;
    }

    public Object method5() {
        System.out.println("method5");

        return null;
    }
}
