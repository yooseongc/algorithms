package chap14;

import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;
import java.util.Arrays;

/**
 * A program with n log n running time.
 * Reads n integers and counts the number of pairs that sum to exactly 0.
 * Ignore integer overflow.
 */
public class TwoSumFast {

    private TwoSumFast() { }

    public static void printAll(int[] a) {
        int n = a.length;
        Arrays.sort(a);
        if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers.");
        for (int i = 0; i < n; i++) {
            int j = Arrays.binarySearch(a, -a[i]);
            if (j > i) StdOut.println(a[i] + " " + a[j]);
        }
    }

    public static int count(int[] a) {
        int n = a.length;
        Arrays.sort(a);
        if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers.");
        int count = 0;
        for (int i = 0; i < n; i++) {
            int j = Arrays.binarySearch(a, -a[i]);
            if (j > i) count++;
        }
        return count;
    }

    private static boolean containsDuplicates(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] == a[i - 1]) return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        In in = ResourceFinder.findResourceInputStream("4Kints.txt");
        int[] a = in.readAllInts();
        Stopwatch timer = new Stopwatch();
        int count = count(a);
        StdOut.println("elapsed time = " + timer.elapsedTime());
        StdOut.println(count);
    }

}
