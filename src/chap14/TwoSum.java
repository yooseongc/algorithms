package chap14;

import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;

/**
 * A program with n^2 running time.
 * Reads n integers and counts the number of pairs that sum to exactly 0.
 * Ignore integer overflow.
 */
public class TwoSum {

    private TwoSum() { }

    public static void printAll(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (a[i] + a[j] == 0) {
                    StdOut.println(a[i] + " " + a[j]);
                }
            }
        }
    }

    public static int count(int[] a) {
        int n = a.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (a[i] + a[j] == 0) {
                    count++;
                }
            }
        }
        return count;
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
