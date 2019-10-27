package chap13p;

import utilities.StdOut;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Problem 1.3.38 using linked list.
 */
public class GeneralizedQueue<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first;
    private Node last;
    private int size;

    public GeneralizedQueue() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (oldLast != null) {
            oldLast.next = last;
        } else {
            first = last;
        }
        size++;
    }

    public Item delete(int k) {
        if (isEmpty()) throw new RuntimeException("Queue underflow.");
        if (k <= 0 || k > size) throw new RuntimeException("Invalid index");
        int count;
        boolean startFromBeginning = (k <= size / 2);
        Node current;
        if (startFromBeginning) {
            count = 1;
            for (current = first; count < k; current = current.next) {
                count++;
            }
        } else {
            count = size;
            for (current = last; count > k; current = current.prev) {
                count--;
            }
        }
        Item item = current.item;
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            first = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            last = current.prev;
        }
        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new GeneralizedQueueIterator();
    }

    private class GeneralizedQueueIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        GeneralizedQueue<Integer> generalizedQueue = new GeneralizedQueue<>();
        generalizedQueue.insert(0);  // 0
        generalizedQueue.insert(1);  // 0 -> 1
        generalizedQueue.insert(2);  // 0 -> 1 -> 2
        generalizedQueue.insert(3);  // 0 -> 1 -> 2 -> 3
        generalizedQueue.insert(4);  // 0 -> 1 -> 2 -> 3 -> 4

        generalizedQueue.delete(1);     // 1 -> 2 -> 3 -> 4
        generalizedQueue.delete(3);     // 1 -> 2 -> 4

        StringJoiner generalizedQueueItems = new StringJoiner(" ");
        for (int item : generalizedQueue) {
            generalizedQueueItems.add(String.valueOf(item));
        }

        StdOut.println("Generalized queue items: " + generalizedQueueItems.toString());
        StdOut.println("Expected: 1 2 4");
    }

}
