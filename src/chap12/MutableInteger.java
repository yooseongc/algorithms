package chap12;

import utilities.StdOut;

import java.lang.reflect.Field;

/**
 * shows that integers are mutable if you allow reflection.
 */
public class MutableInteger {

    public static void main(String[] args) {
        Integer x = 17;
        StdOut.println(x);
        mutate(x);
        StdOut.println(x);
    }

    public static void mutate(Integer x) {
        // change the integer to 999999
        try {
            Field value = Integer.class.getDeclaredField("value");
            value.setAccessible(true);
            value.setInt(x, 999999);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
