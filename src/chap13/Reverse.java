package chap13;

import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

/**
 * Read a sequence of integers from input and print them in reverse order.
 * It is a sample of Stack client.
 */
public class Reverse {

    public static void main(String[] args) {
        In inputs = ResourceFinder.generateInputStream(new String[] { "1", "2", "3", "4", "5" });
        Stack<Integer> stack = new Stack<>();
        while (!inputs.isEmpty()) {
            stack.push(inputs.readInt());
        }
        for (int i : stack) {
            StdOut.println(i);
        }
    }

}
