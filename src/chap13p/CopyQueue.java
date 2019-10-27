package chap13p;

import chap13.Queue;
import utilities.StdOut;

import java.util.StringJoiner;

/**
 * problem 1.3.41
 */
public class CopyQueue {

    public static void main(String[] args) {
        Queue<Integer> originalQueue = new Queue<>();
        originalQueue.enqueue(1);
        originalQueue.enqueue(2);
        originalQueue.enqueue(3);
        originalQueue.enqueue(4);

        Queue<Integer> queueCopy = new Queue<>(originalQueue);
        queueCopy.enqueue(5);
        queueCopy.enqueue(99);

        originalQueue.dequeue();

        queueCopy.dequeue();
        queueCopy.dequeue();

        StringJoiner originalQueueItems = new StringJoiner(" ");
        for (int item : originalQueue) {
            originalQueueItems.add(String.valueOf(item));
        }

        StdOut.println("Original Queue: " + originalQueueItems.toString());
        StdOut.println("Expected: 2 3 4");

        StdOut.println();

        StringJoiner copyQueueItems = new StringJoiner(" ");
        for (int item : queueCopy) {
            copyQueueItems.add(String.valueOf(item));
        }

        StdOut.println("Queue Copy: " + copyQueueItems.toString());
        StdOut.println("Expected: 3 4 5 99");
    }

}
