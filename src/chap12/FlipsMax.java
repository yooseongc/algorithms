package chap12;

import utilities.StdOut;
import utilities.StdRandom;

/**
 * sample client of Counter
 */
public class FlipsMax {

    /**
     * return bigger counter
     */
    public static Counter max(Counter x, Counter y) {
        if (x.tally() > y.tally()) return x;
        else                       return y;
    }

    public static void main(String[] args) {
        if (args.length == 0) args = new String[] { "100000" };

        int n = Integer.parseInt(args[0]);
        Counter heads = new Counter("heads");
        Counter tails = new Counter("tails");
        for (int i = 0; i < n; i++) {
            if (StdRandom.bernoulli(0.5)) heads.increment();
            else                              tails.increment();
        }

        if (heads.tally() == tails.tally()) StdOut.println("Tie");
        else                                StdOut.println(max(heads, tails) + " wins");

    }

}
