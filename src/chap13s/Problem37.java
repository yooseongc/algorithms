package chap13s;

import chap13.Queue;

public class Problem37 {

    public static void main(String[] args) {
        if (args.length == 0) args = new String[] { "7", "2" };
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        Queue<Integer> order = new Queue<>();
        for (int i = 0; i < n; i++) {
            order.enqueue(i);
        }

        while(!order.isEmpty()) {
            for (int i = 0; i < m - 1; i++) {
                order.enqueue(order.dequeue());
            }
            System.out.println(order.dequeue());
        }
    }

}
