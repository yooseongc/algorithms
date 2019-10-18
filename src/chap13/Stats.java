package chap13;

import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

/**
 * Reads in a sequence of real numbers from standard input and computes
 * their mean and standard deviation.
 * <br>
 * It is sample client for Bag
 */
public class Stats {

    public static void main(String[] args) {
        args = new String[] { "100", "99", "101", "120", "98", "107", "109", "81", "101", "90" };
        In inputs = ResourceFinder.generateInputStream(args);
        Bag<Double> numbers = new Bag<>();
        while (!inputs.isEmpty()) {
            numbers.add(inputs.readDouble());
        }
        int n = numbers.size();

        // compute sample mean
        double sum = 0.0;
        for (double x : numbers) {
            sum += x;
        }
        double mean = sum / n;

        // compute sample standard deviation
        sum = 0.0;
        for (double x : numbers) {
            sum += (x - mean) * (x - mean);
        }
        double stddev = Math.sqrt(sum/(n-1));

        StdOut.printf("Mean:    %.2f\n", mean);
        StdOut.printf("Std dev: %.2f\n", stddev);

    }

}
