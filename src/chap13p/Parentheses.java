package chap13p;

import interfaces.Stack;

/**
 *  Problem 1.3.4
 *  Reads in a text file and checks to see if the parentheses are balanced.
 */
public class Parentheses {

    private static final char LEFT_PAREN     = '(';
    private static final char RIGHT_PAREN    = ')';
    private static final char LEFT_BRACE     = '{';
    private static final char RIGHT_BRACE    = '}';
    private static final char LEFT_BRACKET   = '[';
    private static final char RIGHT_BRACKET  = ']';

    public static void main(String[] args) {
        System.out.println(isBalanced("[()]{}{[()()]()}"));
        System.out.println(isBalanced("[(])"));
        assert isBalanced("[()]{}{[()()]()}");
        assert !isBalanced("[(])");
    }

    private static boolean isBalanced(String s) {
        Stack<Character> stack = new chap13.Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == LEFT_PAREN || s.charAt(i) == LEFT_BRACE || s.charAt(i) == LEFT_BRACKET) {
                stack.push(s.charAt(i));
            }
            if (s.charAt(i) == RIGHT_PAREN) {
                if (stack.isEmpty() || stack.pop() != LEFT_PAREN) return false;
            } else if (s.charAt(i) == RIGHT_BRACE) {
                if (stack.isEmpty() || stack.pop() != LEFT_BRACE) return false;
            } else if (s.charAt(i) == RIGHT_BRACKET) {
                if (stack.isEmpty() || stack.pop() != LEFT_BRACKET) return false;
            }
        }
        return stack.isEmpty();
    }


}
