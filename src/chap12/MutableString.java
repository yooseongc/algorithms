package chap12;

import utilities.StdOut;

import java.lang.reflect.Field;

/**
 * shows that Strings are mutable if you allow reflection.
 */
public class MutableString {

    public static void main(String[] args) {
        String s = "Immutable";
        String t = "Notreally";
        mutate(s, t);
        StdOut.println(s);
        StdOut.println(t);

        // strings are interned so this doesn't even print "Immutable"
        StdOut.println("Immutable");
    }

    public static void mutate(String s, String t) {
        try {
            Field val = String.class.getDeclaredField("value");
            val.setAccessible(true);
            char[] value = (char[]) val.get(s);
            for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
                value[i] = t.charAt(i);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
