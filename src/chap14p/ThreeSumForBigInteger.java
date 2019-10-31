package chap14p;

import chap14.Stopwatch;
import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Problem 1.4.2
 * Threesum with BigInteger
 */
public class ThreeSumForBigInteger {

    // prevent instantiation.
    private ThreeSumForBigInteger() { }

    /**
     * Prints to standard output the (i, j, k) with {@code i < j < k}
     * such that {@code a[i] + a[j] + a[k] == 0}.
     *
     * @param a the array of integers
     */
    public static void printAll(int[] a) {
        int n = a.length;
        BigInteger bigInteger;
        // iterate all combination
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    bigInteger = BigInteger.valueOf(a[i])
                            .add(BigInteger.valueOf(a[j]))
                            .add(BigInteger.valueOf(a[k]));
                    if (bigInteger.intValue() == 0) {
                        StdOut.println(a[i] + " " + a[j] + " " + a[k]);
                    }
                }
            }
        }
    }

    /**
     * Returns the number of triples (i, j, k) with {@code i < j < k}
     * such that {@code a[i] + a[j] + a[k] == 0}.
     *
     * @param  a the array of integers
     * @return the number of triples (i, j, k) with {@code i < j < k}
     *         such that {@code a[i] + a[j] + a[k] == 0}
     */
    public static int count(int[] a) {
        int n = a.length;
        int count = 0;
        BigInteger bigInteger;
        // iterate all combination
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    bigInteger = BigInteger.valueOf(a[i])
                            .add(BigInteger.valueOf(a[j]))
                            .add(BigInteger.valueOf(a[k]));
                    if (bigInteger.intValue() == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        In in = ResourceFinder.findResourceInputStream("1Kints.txt");
        int[] a = in.readAllInts();
        Stopwatch timer = new Stopwatch();
        int count = count(a);
        StdOut.println("elapsed time = " + timer.elapsedTime());
        StdOut.println("count = " + count);
    }

}
