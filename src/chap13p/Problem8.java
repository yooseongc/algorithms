package chap13p;

import chap13.ResizingArrayStack;
import interfaces.Stack;
import utilities.In;
import utilities.ResourceFinder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class Problem8 {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        In inputs = ResourceFinder.generateInputStream("it was - the best - of times - - - it was - the - -");
        // 17 elements in input stream.
        Stack<String> stack = new ResizingArrayStack<>();
        while (!inputs.isEmpty()) {
            String input = inputs.readString();
            if (input.equals("-")) stack.pop();
            else                   stack.push(input);
        }
        // 9 - 8 --> 1 elements remain.
        System.out.println(stack);
        System.out.println("size of stack: " + stack.size());
        assert stack.size() == 1;

        /*
            resize 2 --> 4
            resize 4 --> 2
            resize 2 --> 4
            resize 4 --> 2
        */
        Field a = stack.getClass().getDeclaredField("a");
        a.setAccessible(true);
        System.out.println("length of array : " + Array.getLength(a.get(stack)));
        assert 2 == Array.getLength(a.get(stack));
    }

}
