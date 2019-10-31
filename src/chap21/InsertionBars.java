package chap21;

import utilities.StdDraw;
import utilities.StdRandom;

/**
 * Insertion sort n random real numbers between 0 and 1,
 *   visualizing the results by plotting bars with heights proportional to the values.
 */
public class InsertionBars {

    public static void sort(double[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j >= 1 && less(a[j], a[j-1])) {
                exch(a, j, j-1);
                j--;
            }
            show(a, i, j);
        }
    }

    private static void show(double[] a, int i, int j) {
        StdDraw.setYscale(-a.length + i + 0.5, i+1);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        for (int k = 0; k < j; k++) {
            StdDraw.line(k, 0, k,  a[k]*0.6);
        }
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.line(j, 0, j, a[j]*0.6);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int k = j+1; k <= i; k++) {
            StdDraw.line(k, 0, k,  a[k]*0.6);
        }
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        for (int k = i+1; k < a.length; k++) {
            StdDraw.line(k, 0, k, a[k]*0.6);
        }
    }

    private static boolean less(double v, double w) {
        return v < w;
    }

    private static void exch(double[] a, int i, int j) {
        double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int n = 20;
        StdDraw.setCanvasSize(160, 640);
        StdDraw.setXscale(-1, n + 1);
        StdDraw.setPenRadius(0.006);
        double[] a = new double[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform(0.0, 1.0);
        }
        sort(a);
    }

}
