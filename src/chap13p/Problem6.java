package chap13p;

import chap13.Queue;
import chap13.Stack;

import java.util.stream.IntStream;

public class Problem6 {

    public static void main(String[] args) {

        Queue<String> q = new Queue<>();
        IntStream.range(0, 10).forEachOrdered(i -> q.enqueue(i+""));
        System.out.println("before --> " + q);
        Stack<String> s = new Stack<>();
        while (!q.isEmpty()) {
            s.push(q.dequeue());
        }
        while (!s.isEmpty()) {
            q.enqueue(s.pop());
        }
        System.out.println("after --> " + q);  // it may reverse the items on queue

        for (int i = 9; i >= 0; i--) {
            assert q.dequeue().equals(i + "");
        }
    }

}
