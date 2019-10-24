package chap13p;

import chap13.Stack;

/**
 * Problem 1.3.11
 *
 * Evaluates postfix expresions using a stack.
 */
public class EvaluatePostfix {

    public static void main(String[] args) {
        String postfix1 = "2 3 4 + 5 6 * * +";
        int res1 = evaluate(postfix1);
        System.out.println(postfix1 + " => " + res1);
        assert res1 == 212;

        String postfix2 = "5 7 1 1 + * + 3 * 2 1 1 + * +";
        int res2 = evaluate(postfix2);
        System.out.println(postfix2 + " => " + res2);
        assert res2 == 61;
    }

    private static int evaluate(String postfix) {
        String[] terms = postfix.split("\\s");
        Stack<Integer> operands = new Stack<>();
        for (String term : terms) {
            if (term.equals("+")) operands.push(operands.pop() + operands.pop());
            else if (term.equals("*")) operands.push(operands.pop() * operands.pop());
            else operands.push(Integer.parseInt(term));
        }
        return operands.pop();
    }

}
