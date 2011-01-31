package dan.vjtest.sandbox.magic;

import java.lang.reflect.Field;

/**
 * @author Alexander Dovzhikov
 */
public class Ball {
//    public static final String UNDER_HAT = "NONE";
    public static String UNDER_HAT;

    static {
        try {
            new HatTwo().hasBall();

            Field fValue = String.class.getDeclaredField("value");
            fValue.setAccessible(true);
            Field fOffset = String.class.getDeclaredField("offset");
            fOffset.setAccessible(true);
            Field fCount = String.class.getDeclaredField("count");
            fCount.setAccessible(true);

            String internedOne = "ONE";
            String internedTwo = "TWO";

            char[] none = {'N', 'O', 'N', 'E'};
            UNDER_HAT = new String(none);

            fValue.set(internedOne, none);
            fOffset.setInt(internedOne, 0);
            fCount.setInt(internedOne, 4);

            fValue.set(internedTwo, none);
            fOffset.setInt(internedTwo, 0);
            fCount.setInt(internedTwo, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
