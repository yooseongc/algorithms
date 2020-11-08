package chap13p;

import chap13.Queue;
import utilities.StdOut;

/**
 * Problem 1.3.37
 * Josephus problem.
 * In the Josephus problem from antiquity,
 *   N people are in dire straits and agree to the following strategy
 *   to reduce the population.
 * They arrange themselves in a circle (at positions numbered from 0 to N-1)
 *   and proceed around the circle, eliminating every Mth person until only one person is left.
 * Legend has it that Josephus figured out where to sit to avoid being eliminated.
 * Write a Queue client that takes M and N and prints out the order
 *   in which people are eliminated (and thus would show Josephus where to sit in the circle).
 */
public class Josphus {

    public static void main(String[] args) {
        if (args.length == 0) args = new String[] { "7", "2" };
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        // initialize queue
        Queue<Integer> queue = new Queue<>();
        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
        }

        while (!queue.isEmpty()) {
            for (int i = 0; i < m - 1; i++) {
                queue.enqueue(queue.dequeue());
            }
            StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println();
    }

    /**
     *   0 1 2 3 4 5 6  -> r 1
     *   0 ^ 2 3 4 5 6  -> r 3
     *   0 2 ^ 4 5 6    -> r 5
     *   0 2 4 ^ 6      -> r 0
     *   ^ 2 4 6        -> r 4
     *   2 ^ 6          -> r 2
     *   remains : 6
     */

}
