package chap13p;

import chap13.Stack;
import utilities.StdOut;

/**
 * problem 1.3.5
 *
 * change 10-radix to 2-radix
 */
public class Problem5 {

    public static void main(String[] args) {
        int N = 50;
        int M = N;
        Stack<Integer> stack = new Stack<>();
        while (M > 0) {
            stack.push(M % 2);
            M = M / 2;
        }
        StringBuilder result = new StringBuilder();
        for (int d : stack) {
            StdOut.print(d);
            result.append(d);
        }
        StdOut.println();
        System.out.println(Integer.toBinaryString(N));
        assert Integer.toBinaryString(N).equals(result.toString());
    }

}
