package dan.vjtest.sandbox.util;

import java.util.Random;

/**
 * @author Alexander Dovzhikov
 */
public class RandomUtils {
    private RandomUtils() {}
    
    public static int[] randomIntArray(int size) {
        Random r = new Random();
        int[] array = new int[size];
        
        for (int i = 0; i < size; i++)
            array[i] = r.nextInt();

        return array;
    }
}
