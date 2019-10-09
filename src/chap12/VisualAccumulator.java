package chap12;

import utilities.StdDraw;
import utilities.StdOut;
import utilities.StdRandom;

public class VisualAccumulator {

    private double total;
    private int n;

    public VisualAccumulator(int trials, double max) {
        StdDraw.setXscale(0, trials);
        StdDraw.setYscale(0, max);
        StdDraw.setPenRadius(0.005);
    }

    public void addDataValue(double value) {
        n++;
        total += value;
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(n, value);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(n, mean());
    }

    public double mean() {
        return total / n;
    }

    @Override
    public String toString() {
        return "n = " + n + ", mean = " + mean();
    }

    public static void main(String[] args) {
        if (args.length == 0) args = new String[] { "1000" };

        int T = Integer.parseInt(args[0]);

        VisualAccumulator stats = new VisualAccumulator(T, 1.0);
        for (int t = 0; t < T; t++) {
            double x = StdRandom.uniform();
            stats.addDataValue(x);
        }
        StdOut.println(stats);
    }

}
