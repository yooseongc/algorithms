package chap11;

import utilities.StdOut;
import utilities.StdRandom;

public class RandomSeq {

    public static void run(String[] args) {
        int N = Integer.parseInt(args[0]);
        double lo = Double.parseDouble(args[1]);
        double hi = Double.parseDouble(args[2]);

        for (int i = 0; i < N; i++) {
            double x = StdRandom.uniform(lo, hi); // pick random number in range of [lo, hi)
            StdOut.printf("%.2f\n", x);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) args = new String[] { "5", "100.0", "200.0" };
        run(args);
    }

}
