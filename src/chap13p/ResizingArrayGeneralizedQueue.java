package chap13p;

import utilities.StdOut;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Problem 1.3.38 using resizing array.
 */
public class ResizingArrayGeneralizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;

    @SuppressWarnings("unchecked")
    public ResizingArrayGeneralizedQueue() {
        queue = (Item[]) new Object[1];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Item item) {
        if (size == queue.length) resize(queue.length * 2);
        queue[size++] = item;
    }

    public Item delete(int k) {
        if (isEmpty()) throw new RuntimeException("Queue underflow.");
        if (k <= 0 || size < k) throw new RuntimeException("Invalid index");
        Item item = queue[k - 1];
        moveItemsLeft(k);
        size--;
        if (size == queue.length / 4) resize(queue.length / 2);
        return item;
    }

    private void moveItemsLeft(int firstIndex) {
        for (int i = firstIndex; i < size; i++) {
            queue[i - 1] = queue[i];
        }
        queue[size - 1] = null; // to avoid loitering
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }

    @Override
    public Iterator<Item> iterator() {
        return new GeneralizedQueueIterator();
    }

    private class GeneralizedQueueIterator implements Iterator<Item> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Item next() {
            Item item = queue[index];
            index++;
            return item;
        }
    }

    public static void main(String[] args) {
        ResizingArrayGeneralizedQueue<Integer> generalizedQueue = new ResizingArrayGeneralizedQueue<>();
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
