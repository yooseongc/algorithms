package chap13;

import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

/**
 *
 * Arithmetic expression validation.
 * This is a stack client that evaluates fully parenthesized arithmetic expressions.
 * It uses Dijkstra's 2-stack algorithm :
 *     - push operands onto the operand stack
 *     - push operators onto the operator stack
 *     - ignore left parentheses
 *     - on encountering a right parenthesis, pop an operator, pop the requisite number of operands,
 *          and push onto the operand stack the result of applying that operator to those operands.
 *
 *  Note: the operators, operands, and parentheses must be
 *  separated by whitespace. Also, each operation must
 *  be enclosed in parentheses. For example, you must write
 *  ( 1 + ( 2 + 3 ) ) instead of ( 1 + 2 + 3 ).
 *  See EvaluateDeluxe.java for a fancier version.
 *
 *  Remarkably, Dijkstra's algorithm computes the same answer if we put each operator *after* its two operands
 *    instead of * between* them.
 *  Moreover, in such expressions, all parenthesis are redundant!
 *  Removing them yields an expression known as a postfix expression
 *
 *
 * This code is a simple example of an interpreter.
 *
 * ex )
 *    ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )  =>  1 2 3 + 4 5 * * +
 *
 */
public class Evaluate {

    public static void main(String[] args) {

        In inputs = ResourceFinder.generateInputStream(new String[]{ "( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )" });

        Stack<String> operators = new Stack<>();
        Stack<Double> operands = new Stack<>();

        while (!inputs.isEmpty()) {
            String s = inputs.readString();
            if      (s.equals("("))  ; // do nothing.
            else if (s.equals("+")) operators.push(s);
            else if (s.equals("-")) operators.push(s);
            else if (s.equals("*")) operators.push(s);
            else if (s.equals("/")) operators.push(s);
            else if (s.equals("sqrt")) operators.push(s);
            else if (s.equals(")")) {
                String operator = operators.pop();
                double operand  = operands.pop();
                if      (operator.equals("+")) operand = operands.pop() + operand;
                else if (operator.equals("-")) operand = operands.pop() - operand;
                else if (operator.equals("*")) operand = operands.pop() * operand;
                else if (operator.equals("/")) operand = operands.pop() / operand;
                else if (operator.equals("sqrt")) operand = Math.sqrt(operand);
                operands.push(operand);
            }
            else operands.push(Double.parseDouble(s));
        }
        StdOut.println(operands.pop());
    }

}
