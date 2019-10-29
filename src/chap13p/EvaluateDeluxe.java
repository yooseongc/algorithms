package chap13p;

import chap13.Stack;
import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.util.TreeMap;

/**
 * Problem 1.3.51
 *  Evaluates arithmetic expressions using Dijkstra's two-stack algorithm.
 *  Handles the following binary operators: +, -, *, / and parentheses.
 *
 *  Limitations
 *  --------------
 *    -  can easily add additional operators and precedence orders, but they
 *       must be left associative (exponentiation is right associative)
 *    -  assumes whitespace between operators (including parentheses)
 *
 *  Remarks
 *  --------------
 *    -  can eliminate second phase if we enclose input expression
 *       in parentheses (and, then, could also remove the test
 *       for whether the operator stack is empty in the inner while loop)
 *    -  see http://introcs.cs.princeton.edu/java/11precedence/ for
 *       operator precedence in Java
 */
public class EvaluateDeluxe {

    private static TreeMap<String, Integer> precedence;

    static {
        // precedence order of operators
        precedence = new TreeMap<>();
        precedence.put("(", 0);   // for convenience with algorithm
        precedence.put(")", 0);
        precedence.put("+", 1);   // + and - have lower precedence than * and /
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
    }

    private static double eval(String op, double val1, double val2) {
        if (op.equals("+")) return val1 + val2;
        if (op.equals("-")) return val1 - val2;
        if (op.equals("/")) return val1 / val2;
        if (op.equals("*")) return val1 * val2;
        throw new RuntimeException("Invalid operator");
    }

    public static void main(String[] args) {
        In inputs = ResourceFinder.generateInputStream("3 + 5 * 6 - 7 * ( 8 + 5 )");
        Stack<String> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();

        while (!inputs.isEmpty()) {
            // read token
            String s = inputs.readString();

            // token is a value
            if (!precedence.containsKey(s)) {
                vals.push(Double.parseDouble(s));
                continue;
            }
            // token is an operator
            while (true) {
                // the last condition ensures that the operator with higher precedence is evaluated first
                if (ops.isEmpty() || s.equals("(") || (precedence.get(s) > precedence.get(ops.peek()))) {
                    ops.push(s);
                    break;
                }
                // evaluate expression
                String op = ops.pop();
                if (op.equals("(")) {
                    assert s.equals(")");
                    break;
                } else {
                    double val2 = vals.pop();
                    double val1 = vals.pop();
                    vals.push(eval(op, val1, val2));
                }
            }
        }

        // finished parsing string - evaluate operator and operands remaining on two stacks
        while (!ops.isEmpty()) {
            String op = ops.pop();
            double val2 = vals.pop();
            double val1 = vals.pop();
            vals.push(eval(op, val1, val2));
        }

        // last value on stack is value of expression
        StdOut.println(vals.pop());
        assert vals.isEmpty();
        assert ops.isEmpty();
    }

}
