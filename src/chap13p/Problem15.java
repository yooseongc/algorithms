package chap13p;

import chap13.Queue;
import utilities.StdOut;

public class Problem15 {

    private static void printItems(Queue<String> queue, int k) {
        int count = 0;
        for (String item : queue) {
            count++;
            if (count == k) {
                StdOut.println(item);
                break;
            }
        }
    }

    public static void main(String[] args) {
        int k = 4;
        String input = "A B C D E F";
        String[] inputs = input.split("\\s");
        Queue<String> queue = new Queue<>();
        for (String s : inputs) {
            queue.enqueue(s);
        }
        printItems(queue, k);
    }

}
