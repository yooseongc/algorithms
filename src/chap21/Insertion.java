package chap21;

import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;
import java.util.Comparator;

/**
 * The {@code Insertion} class provides static methods for sorting an array
 *   using insertion sort.
 * <p>
 * Sorts a sequence of strings from standard input using insertion sort.
 * <p>
 * This implementation makes ~ 1/2 N^2 compares and exchanges in the worst case,
 *   so it is not suitable for sorting large arbitrary arrays.
 * More precisely,the number of exchanges is exactly equal to the number
 *   of inversions. So, for example, it sorts a partially-sorted array in
 *   linear time.
 * <p>
 * The sorting algorithm is stable and uses O(1) extra memory.
 *
 */
public class Insertion {

    private Insertion() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }

    /**
     * Rearranges the array in ascending order, using a comparator.
     * @param a the array to be sorted
     * @param comparator the comparator specifying the order
     */
    public static void sort(Object[] a, Comparator comparator) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(comparator, a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
            assert isSorted(a, comparator, 0, i);
        }
        assert isSorted(a, comparator);
    }

    /**
     * Rearranges the subarray a[lo .. hi) in ascending order, using a comparator.
     * @param a the array to be sorted
     * @param lo left endpoint (inclusive)
     * @param hi right endpoint (exclusive)
     * @param comparator the comparator specifying the order
     */
    public static void sort(Object[] a, int lo, int hi, Comparator comparator) {
        for (int i = lo + 1; i < hi; i++) {
            for (int j = i; j > lo && less(comparator, a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
        }
        assert isSorted(a, comparator, lo, hi);
    }

    /**
     * Returns a permutation that gives the elements in the array in ascending order.
     * @param a the array
     * @return a permutation {@code p[]} such taht {@code a[p[0]]}, {@code a[p[1]]},
     *   ..., {@code a[p[n-1]]} are in ascending order
     */
    public static int[] indexSort(Comparable[] a) {
        int n = a.length;
        int[] index = new int[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(a[index[j]], index[j-1]); j--) {
                exch(index, j, j-1);
            }
        }
        return index;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean less(Comparator comparator, Object v, Object w) {
        return comparator.compare(v, w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    private static boolean isSorted(Object[] a, Comparator comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(comparator, a[i], a[i - 1])) return false;
        }
        return true;
    }

    private static void show(Comparable[] a) {
        for (Comparable comparable : a) {
            StdOut.println(comparable);
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
            Insertion.sort(a);
            show(a);
            StdOut.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
