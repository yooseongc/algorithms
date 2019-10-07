package chap12;

import utilities.StdOut;
import utilities.StdRandom;

public class Counter implements Comparable<Counter> {

    private final String name;  // the name of counter
    private int count = 0;      // current value

    public Counter(String name) {
        this.name = name;
    }

    public void increment() {
        count++;
    }

    public int tally() {
        return count;
    }

    @Override
    public String toString() {
        return count + " " + name;
    }

    @Override
    public int compareTo(Counter that) {
        if      (this.count < that.count) return -1;
        else if (this.count > that.count) return +1;
        else                              return  0;
    }

    public static void main(String[] args) {
        if (args.length == 0) args = new String[] { "6", "600000" };
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        // create n counters
        Counter[] hits = new Counter[n];
        for (int i = 0; i < n; i++) {
            hits[i] = new Counter("counter"+ i);
        }

        // random trial
        for (int t = 0; t < trials; t++) {
            hits[StdRandom.uniform(n)].increment();
        }

        // print
        for (Counter hit : hits) {
            StdOut.println(hit);
        }
    }

}
