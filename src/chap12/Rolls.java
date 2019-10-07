package chap12;

import utilities.StdOut;
import utilities.StdRandom;

/**
 * sample client of Counter
 */
public class Rolls {

    public static void main(String[] args) {
        if (args.length == 0) args = new String[] { "100000" };

        int n = Integer.parseInt(args[0]);
        int SIDES = 6;

        // initialize counter
        Counter[] rolls = new Counter[SIDES + 1];
        for (int i = 1; i <= SIDES; i++) {
            rolls[i] = new Counter(i + "s");
        }

        // flip dice
        for (int j = 0; j < n; j++) {
            int result = StdRandom.uniform(1, SIDES + 1); // 1 ~ 6
            rolls[result].increment();
        }

        // print results
        for (int i = 1; i <= SIDES; i++) {
            StdOut.println(rolls[i]);
        }

    }

}
