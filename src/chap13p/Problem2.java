package chap13p;

import interfaces.Stack;
import utilities.In;
import utilities.ResourceFinder;

import java.util.ArrayList;
import java.util.List;

public class Problem2 {

    public static void main(String[] args) {
        In inputs = ResourceFinder.generateInputStream("it was - the best - of times - - - it was - the - -");
        Stack<String> stack = new chap13.Stack<>();
        List<String> popped = new ArrayList<>();
        while (!inputs.isEmpty()) {
            String s = inputs.readString();
            if (s.equals("-")) popped.add(stack.pop());
            else stack.push(s);
        }

        System.out.println(String.join(" ", popped));
        System.out.println(stack);
        assert "was best times of the was the it".equals(String.join(" ", popped));
        assert stack.size() == 1 && stack.peek().equals("it");
    }

}
