package chap21;

import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;

/**
 * The {@code Shell} class provides static methods for sorting an array
 *   using Shellsort with Knuth's increment sequence (1, 4, 13, 40, ...).
 *   ==> next = 3 * current + 1
 */
public class Shell {

    private Shell() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        int n = a.length;
        int h = 1;
        while (h < n/3) {
            h = 3 * h + 1;  // 1, 4, 13, 40, 121, ...
        }
        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    exch(a, j, j-h);
                }
            }
            assert isHsorted(a, h);
            h /= 3;
        }
        assert isSorted(a);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        }
        return true;
    }

    private static boolean isHsorted(Comparable[] a, int h) {
        for (int i = h; i < a.length; i++) {
            if (less(a[i], a[i-h])) return false;
        }
        return true;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        test("tiny.txt");
        test("words3.txt");

    }

    private static void test(String filename) {
        try {
            StdOut.println("sort: " + filename);
            In inputs = ResourceFinder.findResourceInputStream(filename);
            String[] a = inputs.readAllStrings();
            Shell.sort(a);
            show(a);
            StdOut.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
