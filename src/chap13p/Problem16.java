package chap13p;

import chap12.Date;
import chap13.Queue;
import utilities.StdIn;

public class Problem16 {

    private static Date[] readAllDates() {
        Queue<Date> queue = new Queue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(new Date(StdIn.readString()));
        }
        int N = queue.size();
        Date[] dates = new Date[N];
        for (int i = 0; i < N; i++) {
            dates[i] = queue.dequeue();
        }
        return dates;
    }

    public static void main(String[] args) {
        readAllDates();
    }

}
