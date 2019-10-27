package chap13p;

import chap13.Queue;
import utilities.StdOut;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Problem 1.3.39
 * RingBuffer : circular queue with fixed size N.
 */
public class RingBuffer<Item> implements Iterable<Item> {

    private Item[] ringBuffer;
    private int size;
    private int first;
    private int last;

    private Queue<Item> producerAuxBuffer;
    private int dataCountToBeConsumed;

    @SuppressWarnings("unchecked")
    public RingBuffer(int capacity) {
        ringBuffer = (Item[]) new Object[capacity];
        size = 0;
        first = -1;
        last = -1;

        producerAuxBuffer = new Queue<>();
        dataCountToBeConsumed = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void produce(Item item) {
        if (dataCountToBeConsumed > 0) {
            // there is data to be consumed
            consumeData(item);
            dataCountToBeConsumed--;
        } else {
            if (isEmpty()) {
                ringBuffer[size] = item;
                first = 0;
                last = 0;
                size++;
            } else {
                if (size < ringBuffer.length) {
                    if (last == ringBuffer.length - 1) {
                        last = 0; // wrap around
                    } else {
                        last++;
                    }
                    ringBuffer[last] = item;
                    size++;
                } else {
                    // RingBuffer is full - add to auxiliary producer buffer
                    producerAuxBuffer.enqueue(item);
                }
            }
        }
    }

    private void consumeData(Item item) {
        // consumer consumes the item
        StdOut.print("Data consumed: " + item);
    }

    public Item consume() {
        if (isEmpty()) {
            dataCountToBeConsumed++;
            return null;
        }
        Item item = ringBuffer[first];
        ringBuffer[first] = null; // to avoid loitering
        if (first == ringBuffer.length - 1) {
            first = 0;  // wrap around
        } else {
            first++;
        }
        size--;
        if (!producerAuxBuffer.isEmpty()) {
            produce(producerAuxBuffer.dequeue());
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RingBufferIterator();
    }

    private class RingBufferIterator implements Iterator<Item> {

        private int current = first;
        private int count = 0;

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public Item next() {
            Item item = ringBuffer[current];

            if (current == ringBuffer.length - 1) {
                current = 0; //Wrap around
            } else {
                current++;
            }

            count++;
            return item;
        }

    }

    public static void main(String[] args) {
        RingBuffer<Integer> ringBuffer = new RingBuffer<>(4);
        ringBuffer.produce(0); // 0
        ringBuffer.produce(1); // 0 1
        ringBuffer.produce(2); // 0 1 2
        ringBuffer.produce(3); // 0 1 2 3
        ringBuffer.produce(4); // 0 1 2 3, q: 4
        ringBuffer.produce(5); // 0 1 2 3, q: 5 -> 4

        Integer item1 = ringBuffer.consume();  // return 0,  1 2 3 4, q: 5
        if (item1 != null) StdOut.println("Consumed " + item1);
        StdOut.println("Expected: 0\n");

        Integer item2 = ringBuffer.consume();  // return 1, 2 3 4 5
        if (item2 != null) StdOut.println("Consumed " + item2);
        StdOut.println("Expected: 1\n");

        ringBuffer.produce(6);  // 2 3 4 5, q: 6
        ringBuffer.produce(7);  // 2 3 4 5, q: 7 -> 6

        StringJoiner ringBufferItems = new StringJoiner(" ");
        for (int item : ringBuffer) {
            ringBufferItems.add(String.valueOf(item));
        }
        StdOut.println("Main ring buffer items: " + ringBufferItems.toString());
        StdOut.println("Expected: 2 3 4 5");
    }

}
