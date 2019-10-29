package chap13p;

import chap13.Stack;
import utilities.StdOut;

/**
 * Problem 1.3.45 StackGenerability
 *
 * Suppose that we have a sequence of intermixed push and pop operations as with our test stack client,
 *   where the integers 0, 1, ..., N-1 in that order (push directives) are intermixed with N minus signs (pop directives).
 * Devise an algorithm that determines whether the intermixed sequence causes the stack to underflow.
 * (You may use only an amount of space independent of N — you cannot store the integers in a data structure.)
 * Devise a linear-time algorithm that determines whether a given permutation can be generated as output
 *   by our test client (depending on where the pop operations occur).
 *
 *
 * Solution.
 * The stack does not underflow unless there exists an integer k
 *   such that the first k pop operations occur before the first k push operations.
 * If a given permutation can be generated, it is uniquely generated as follows:
 *   if the next integer in the permutation is in the top of the stack, pop it;
 *   otherwise, push the next integer in the input sequence onto the stack (or stop if N-1 has already been pushed).
 * The permutation can be generated if and only if the stack is empty upon termination.
 */
public class StackGenerability {

    private static boolean willTheStackUnderflow(String[] inputValues) {
        int itemCount = 0;
        for (String input : inputValues) {
            if (input.equals("-")) {
                itemCount--;
            } else {
                itemCount++;
            }
            if (itemCount < 0) return true;
        }
        return false;
    }

    private static boolean canPermutationBeGenerated(String[] permutation) {
        Stack<Integer> stack = new Stack<>();
        int current = 0;
        for (String value : permutation) {
            int integerValue = Integer.parseInt(value);
            if (stack.isEmpty() || integerValue > stack.peek()) {
                while (current < integerValue) {
                    stack.push(current);
                    current++;
                }
                current++;
            } else if (integerValue == stack.peek()) {
                stack.pop();
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        //"Will the stack underflow?" tests
        String input1Values = "0 1 2 - - -";
        String[] input1 = input1Values.split("\\s");

        StdOut.print("Input 1 - Will Underflow? ");
        StdOut.println(willTheStackUnderflow(input1) + " Expected: false");

        String input2Values = "0 1 2 - - - 3 4 5 - - 6 - - -";
        String[] input2 = input2Values.split("\\s");

        StdOut.print("Input 2 - Will Underflow? ");
        StdOut.println(willTheStackUnderflow(input2) + " Expected: true");

        String input3Values = "0 - - 1 2 -";
        String[] input3 = input3Values.split("\\s");

        StdOut.print("Input 3 - Will Underflow? ");
        StdOut.println(willTheStackUnderflow(input3) + " Expected: true");

        //"Can permutation be generated?" tests
        StdOut.println("\nCan a permutation be generated?");
        StdOut.print("Input: 4 3 2 1 0 9 8 7 6 5 - ");
        StdOut.println(canPermutationBeGenerated("4 3 2 1 0 9 8 7 6 5".split(" ")) + " Expected: true");
        StdOut.print("Input: 4 6 8 7 5 3 2 9 0 1 - ");
        StdOut.println(canPermutationBeGenerated("4 6 8 7 5 3 2 9 0 1".split(" ")) + " Expected: false");
        StdOut.print("Input: 2 5 6 7 4 8 9 3 1 0 - ");
        StdOut.println(canPermutationBeGenerated("2 5 6 7 4 8 9 3 1 0".split(" ")) + " Expected: true");
        StdOut.print("Input: 4 3 2 1 0 5 6 7 8 9 - ");
        StdOut.println(canPermutationBeGenerated("4 3 2 1 0 5 6 7 8 9".split(" ")) + " Expected: true");
        StdOut.print("Input: 1 2 3 4 5 6 9 8 7 0 - ");
        StdOut.println(canPermutationBeGenerated("1 2 3 4 5 6 9 8 7 0".split(" ")) + " Expected: true");
        StdOut.print("Input: 0 4 6 5 3 8 1 7 2 9 - ");
        StdOut.println(canPermutationBeGenerated("0 4 6 5 3 8 1 7 2 9".split(" ")) + " Expected: false");
        StdOut.print("Input: 1 4 7 9 8 6 5 3 0 2 - ");
        StdOut.println(canPermutationBeGenerated("1 4 7 9 8 6 5 3 0 2".split(" ")) + " Expected: false");
        StdOut.print("Input: 2 1 4 3 6 5 8 7 9 0 - ");
        StdOut.println(canPermutationBeGenerated("2 1 4 3 6 5 8 7 9 0".split(" ")) + " Expected: true");
        StdOut.print("Input: 4 3 2 1 0 5 9 7 8 6 - ");
        StdOut.println(canPermutationBeGenerated("4 3 2 1 0 5 9 7 8 6".split(" ")) + " Expected: false");
    }

}
