package chap13s;

import chap13.Stack;

import java.util.Arrays;
import java.util.Objects;

public class Problem9 {


    private static String addLeftParenthesis(String input) {
        String[] tokens = tokenize(input);
        Stack<String> ops = new Stack<>();
        Stack<String> numbers = new Stack<>();
        for (String token : tokens) {
            if (isOperator(token)) {
                ops.push(token);
            } else if (isRightParenthesis(token)) {
                String rightNumber = numbers.pop();
                String leftNumber = numbers.pop();
                String op = ops.pop();
                numbers.push(String.format("( %s %s %s )", leftNumber, op, rightNumber));
            } else {
                numbers.push(token);
            }
        }

        return String.join(" ", numbers);
    }

    private static boolean isRightParenthesis(String token) {
        return ")".equals(token);
    }

    private static boolean isOperator(String token) {
        return Arrays.asList(new String[]{"+", "-", "*", "/"}).contains(token);
    }

    private static String[] tokenize(String equation) {
        return equation.split(" ");
    }

    public static void main(String[] args) {
        /**
         * 우측 괄호가 빠진 수식을 입력받아, 괄호쌍에 맞게끔 수식을 고치는 프로그램의 작성
         */

        String input = "1 + 2 ) + 3 - 4 ) * 5 - 6 ) ) )";
        String answer = "( ( 1 + 2 ) + ( ( 3 - 4 ) * ( 5 - 6 ) ) )";

        String output = addLeftParenthesis(input);
        System.out.println(output);
        assert Objects.equals(output, answer);

    }

}
