package chap13p;

import chap13.Stack;

/**
 * Problem 1.3.10
 *
 *  Reads in a fully parenthesized infix expression
 *  and prints an equivalent postfix expression.
 */
public class InfixToPostfix {

    public static void main(String[] args) {

        String infix1 = "( 2 + ( ( 3 + 4 ) * ( 5 * 6 ) ) )";
        String postfix1 = "2 3 4 + 5 6 * * +";
        String res1 = infixToPostfix(infix1);
        assert postfix1.equals(res1);

        String infix2 = "( ( ( 5 + ( 7 * ( 1 + 1 ) ) ) * 3 ) + ( 2 * ( 1 + 1 ) ) )";
        String postfix2 = "5 7 1 1 + * + 3 * 2 1 1 + * +";
        String res2 = infixToPostfix(infix2);
        assert postfix2.equals(res2);
    }

    private static String infixToPostfix(String infix) {
        Stack<String> stack = new Stack<>();
        String[] terms = infix.split("\\s");
        StringBuilder postfix = new StringBuilder();
        for (String term : terms) {
            if (term.equals("+") || term.equals("*")) stack.push(term);
            else if (term.equals(")")) postfix.append(stack.pop()).append(" ");
            else if (term.equals("(")) ; // do nothing
            else postfix.append(term).append(" ");
        }
        return postfix.toString().trim();
    }

}
