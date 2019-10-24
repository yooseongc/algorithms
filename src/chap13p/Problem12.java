package chap13p;


import chap13.Stack;
import utilities.StdOut;

/**
 * implement copy of Stack[String]
 */
public class Problem12 {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.push("First Item");
        stack.push("Second Item");
        stack.push("Third Item");

        Stack<String> copy = copy(stack);
        stack.pop();
        stack.pop();

        for (String s : copy) {
            StdOut.println(s);
        }

        StdOut.println("\nExpected: " +
                "\nThird Item\n" +
                "Second Item\n" +
                "First Item");
    }

    private static Stack<String> copy(Stack<String> stack) {
        Stack<String> temp = new chap13.Stack<>();
        Stack<String> copy = new chap13.Stack<>();
        for (String s : stack) {
            temp.push(s);
        }
        for (String s : temp) {
            copy.push(s);
        }
        return copy;
    }

}
