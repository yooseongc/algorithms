package chap12;

import utilities.StdOut;
import utilities.StdRandom;

import java.io.IOException;

/**
 * Mutable data type that calculates the mean, sample stddev, and sample variance
 * of a stream of real numbers.
 * Use a stable, one-pass algorithm.
 */
public class Accumulator {

    private int n = 0;         // number of data values
    private double sum = 0.0;  // sample variance * (n - 1)
    private double mu  = 0.0;  // sample mean

    public Accumulator() { }

    /**
     * adds the specified data value to the accumulator.
     *   see https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Online_algorithm
     *   see https://en.wikipedia.org/wiki/Variance#Sample_variance for unviased sample variance
     *
     * if a comes,
     *     n = 1
     *     delta = a - 0.0 = a
     *     mu    = 0.0 + a / 1 = a
     *     sum   = 0 / 1 * a * a = 0
     * if b comes after a,
     *     n = 2
     *     delta = b - a
     *     mu = a + (b - a) / 2 = (a + b) / 2
     *     sum   = 0 + (2 - 1) / 2 * (b - a)^2 = 1/2 * (b - a) * (b - (a+b)/2) = 1/2 * (b-a)^2 / 2
     *     variance * (n - 1) = (2 - 1) / 2 * { [a - (a+b)/2 ]^2 + [b - (a+b)/2 ]^2 }
     *                        = (2 - 1) / 2 * { [ (a - b) / 2 ]^2 + [ (b - a) / 2]^2 }
     *                        = (2 - 1) / 2 * (b - a)^2 / 2
     */
    public void addDataValue(double x) {
        n++;
        double delta = x - mu;
        mu += delta / n;
        double delta2 = x - mu;
        sum += (double) (n - 1) / n * delta * delta2;
    }

    public double mean() { return mu; }

    public double var() {
        if (n <= 1) return Double.NaN;
        return sum / (n - 1);
    }

    public double stddev() {
        return Math.sqrt(this.var());
    }

    public int count() { return n; }

    @Override
    public String toString() {
        return "n = " + n + ", mean = " + mean() + ", stddev = " + stddev();
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) args = new String[] { "1000" };

        int T = Integer.parseInt(args[0]);

        Accumulator stats = new Accumulator();
        for (int t = 0; t < T; t++) {
            double x = StdRandom.uniform();
            stats.addDataValue(x);
        }

        StdOut.printf("n      = %d\n",   stats.count());
        StdOut.printf("mean   = %.5f\n", stats.mean());
        StdOut.printf("stddev = %.5f\n", stats.stddev());
        StdOut.printf("var    = %.5f\n", stats.var());
        StdOut.println(stats);
    }

}
