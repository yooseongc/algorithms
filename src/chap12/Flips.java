package chap12;

import utilities.StdOut;
import utilities.StdRandom;

/**
 *  sample client for Counter
 */
public class Flips {

    public static void main(String[] args) {
        if (args.length == 0) args = new String[] { "100000" };

        // flip coin n times.
        //    use bernoulli trial
        int n = Integer.parseInt(args[0]);
        Counter heads = new Counter("heads");
        Counter tails = new Counter("tails");
        for (int i = 0; i < n; i++) {
            if (StdRandom.bernoulli(0.5)) heads.increment();
            else                              tails.increment();
        }

        // print
        StdOut.println(heads);
        StdOut.println(tails);
        int delta = heads.tally() - tails.tally();
        StdOut.println("delta: " + Math.abs(delta));
    }

}
