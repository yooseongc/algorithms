package chap13p;

import chap13.Stack;
import utilities.StdOut;

import java.util.StringJoiner;

/**
 * Problem 1.3.42
 */
public class CopyStack {

    public static void main(String[] args) {
        Stack<Integer> originalStack = new Stack<>();
        originalStack.push(1);
        originalStack.push(2);
        originalStack.push(3);
        originalStack.push(4);

        Stack<Integer> stackCopy = new Stack<>(originalStack);

        stackCopy.push(5);
        stackCopy.push(6);

        originalStack.pop();
        originalStack.pop();

        stackCopy.pop();

        StringJoiner originalStackItems = new StringJoiner(" ");
        for (int item : originalStack) {
            originalStackItems.add(String.valueOf(item));
        }

        StdOut.println("Original Stack: " + originalStackItems.toString());
        StdOut.println("Expected: 2 1");

        StdOut.println();

        StringJoiner copyStackItems = new StringJoiner(" ");
        for (int item : stackCopy) {
            copyStackItems.add(String.valueOf(item));
        }

        StdOut.println("Stack Copy: " + copyStackItems.toString());
        StdOut.println("Expected: 5 4 3 2 1");
    }

}
