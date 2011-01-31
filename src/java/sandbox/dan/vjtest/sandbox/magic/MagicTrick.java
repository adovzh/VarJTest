package dan.vjtest.sandbox.magic;

/**
 * @author Alexander Dovzhikov
 */
public class MagicTrick {
    public static void main(String[] args) {
        HatOne hatOne = new HatOne();
        HatTwo hatTwo = new HatTwo();

        if (hatOne.hasBall() && hatTwo.hasBall())
            System.out.println("TADA!! The ball is under both hats!");
        else if (hatOne.hasBall())
            System.out.println("The ball is under hat one.");
        else if (hatTwo.hasBall())
            System.out.println("The ball is under hat two.");
        else
            System.out.println("Better luck next time.");
    }
}
