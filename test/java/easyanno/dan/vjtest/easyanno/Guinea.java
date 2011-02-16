package dan.vjtest.easyanno;

/**
 * @author Alexander Dovzhikov
 */
public class Guinea {

    public static void main(String[] args) throws InterruptedException {
        Guinea g = new Guinea();

        try {
            g.greetMethod();
            g.byeMethod();
            g.anotherGreetMethod();
            g.notNullMethod(1);
            g.notNullSpecialMethod();
        } catch (Exception e) {
            Thread.sleep(500L);
            e.printStackTrace();
        }
    }

    @Greet
    public void greetMethod() {
        System.out.println("greetMethod");
    }

    @Bye
    public void byeMethod() {
        System.out.println("byeMethod");
    }

    @Greet
    @Bye
    public void anotherGreetMethod() {
        System.out.println("anotherGreetMethod");
    }

    @NotNull
    public Object notNullMethod(int code) {
        Object var = null;
        System.out.println("notNullMethod");

        if (var == null) {
            var = (code == 1) ? new Object() : null;
        }

        return var;
    }

    @NotNull("Method is supposed to return null")
    public Object notNullSpecialMethod() {
        System.out.println("notNullSpecialMethod");

        return null;
    }
}
