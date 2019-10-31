package chap21;

import chap14.Stopwatch;
import utilities.StdOut;
import utilities.StdRandom;

/**
 * Sort n random real numbers, trials times using the two algorithms
 *   specified on the command line.
 *
 *  Note: this program is designed to compare two sorting algorithms with
 *  roughly the same order of growth, e,g., insertion sort vs. selection
 *  sort or mergesort vs. quicksort. Otherwise, various system effects
 *  (such as just-in-time compiliation) may have a significant effect.
 *  One alternative is to execute with "java -Xint", which forces the JVM
 *  to use interpreted execution mode only.
 */
public class SortCompare {

    public static double time(String algorithm, Double[] a) {
        Stopwatch stopwatch = new Stopwatch();
        if      (algorithm.equals("Insertion"))       Insertion.sort(a);
//        else if (algorithm.equals("InsertionX"))      InsertionX.sort(a);
//        else if (algorithm.equals("BinaryInsertion")) BinaryInsertion.sort(a);
        else if (algorithm.equals("Selection"))       Selection.sort(a);
//        else if (algorithm.equals("Bubble"))          Bubble.sort(a);
//        else if (algorithm.equals("Shell"))           Shell.sort(a);
//        else if (algorithm.equals("Merge"))           Merge.sort(a);
//        else if (algorithm.equals("MergeX"))          MergeX.sort(a);
//        else if (algorithm.equals("MergeBU"))         MergeBU.sort(a);
//        else if (algorithm.equals("Quick"))           Quick.sort(a);
//        else if (algorithm.equals("Quick3way"))       Quick3way.sort(a);
//        else if (algorithm.equals("QuickX"))          QuickX.sort(a);
//        else if (algorithm.equals("Heap"))            Heap.sort(a);
//        else if (algorithm.equals("System"))          Arrays.sort(a);
        else throw new IllegalArgumentException("Invalid algorithm: " + algorithm);
        return stopwatch.elapsedTime();
    }

    public static double timeRandomInput(String algorithm, int n, int trials) {
        double total = 0.0;
        Double[] a = new Double[n];
        for (int t = 0; t < trials; t++) {
            for (int i = 0; i < n; i++) {
                a[i] = StdRandom.uniform(0.0, 1.0);
            }
            total += time(algorithm, a);
        }
        return total;
    }

    public static double timeSortedInput(String algorithm, int n, int trials) {
        double total = 0.0;
        Double[] a = new Double[n];
        for (int t = 0; t < trials; t++) {
            for (int i = 0; i < n; i++) {
                a[i] = 1.0 * i;
            }
            total += time(algorithm, a);
        }
        return total;
    }

    public static void main(String[] args) {
        test("Insertion", "Selection", 1000, 100);
    }

    private static void test(String algorithm1, String algorithm2, int n, int trials) {
        double time1, time2;
        // sorted input
        StdOut.println("Test for sorted input");
        time1 = timeSortedInput(algorithm1, n, trials);   // Total for alg1.
        time2 = timeSortedInput(algorithm2, n, trials);   // Total for alg2.
        StdOut.printf("For %d sorted Doubles\n    %s is", n, algorithm1);
        StdOut.printf(" %.1f times faster than %s\n", time2/time1, algorithm2);

        // random input
        StdOut.println("Test for random input");
        time1 = timeRandomInput(algorithm1, n, trials);   // Total for alg1.
        time2 = timeRandomInput(algorithm2, n, trials);   // Total for alg2.
        StdOut.printf("For %d random Doubles\n    %s is", n, algorithm1);
        StdOut.printf(" %.1f times faster than %s\n", time2/time1, algorithm2);
    }

}
