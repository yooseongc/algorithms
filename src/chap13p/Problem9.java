package chap13p;

import chap13.Stack;

public class Problem9 {

    public static void main(String[] args) {
        String result = getInfixExpression("1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )");
        System.out.println(result);
        assert result.equals("( ( 1 + 2 ) * ( ( 3 - 4 ) * ( 5 - 6 ) ) )");
    }

    private static String getInfixExpression(String s) {
        Stack<String> operands = new Stack<>();
        Stack<String> operators = new Stack<>();
        String[] inputs = s.split("\\s");
        for (String input : inputs) {
            if (input.equals(")")) {
                String operator = operators.pop();
                String v2 = operands.pop();
                String v1 = operands.pop();
                String subExpression = String.format("( %s %s %s )", v1, operator, v2);
                operands.push(subExpression);
            } else if (input.matches("^[+\\-*/]$")) {
                operators.push(input);
            } else {
                operands.push(input);
            }
        }
        return operands.pop();
    }

}
