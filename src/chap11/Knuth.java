package chap11;

import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;
import java.util.Arrays;

/**
 * Reads in a list of strings and prints them in random order.
 * The Knuth (or Fisher-Yates) shuffling algorithm guarantees
 * to rearrange the elements in uniformly random order, under
 * the assumption that Math.random() generates independent and
 * uniformly distributed numbers between 0 and 1.
 *
 * see https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
 */
public class Knuth {

    private Knuth() { }

    /**
     * Rearranges an array of objects in uniformly random order
     * (under the assumption that {@code Math.random()} generates independent
     * and uniformly distributed numbers between 0 and 1).
     * @param a the array to be shuffled.
     */
    public static void shuffle(Object[] a) {
        int n = a.length;
        for (int i = n-1; i >= 0; i--) {
            // choose index uniformly in [0, i]
            int r = (int) (Math.random() * (i + 1));
            Object swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
    }

    /**
     * Rearranges an array of objects in uniformly random order
     * (under the assumption that {@code Math.random()} generates independent
     * and uniformly distributed numbers between 0 and 1).
     * @param a the array to be shuffled.
     */
    public static void shuffleAlternate(Object[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            // choose index uniformly in [i, n-1]
            int r = i + (int) (Math.random() * (n - i));
            Object swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
    }

    public static void main(String[] args) throws IOException {

        // test 1
        String[] a = ResourceFinder.findResourceInputStream("cards.txt").readAllStrings();
        Knuth.shuffle(a);
        Arrays.stream(a).forEach(StdOut::println);

        // test 2
        a = ResourceFinder.findResourceInputStream("cardsUnicode.txt").readAllStrings();
        Knuth.shuffle(a);
        Arrays.stream(a).forEach(StdOut::println);
    }

}
