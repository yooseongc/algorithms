package chap13p;

import utilities.StdRandom;

import java.util.Iterator;

/**
 * Problem 1.3.35, 1.3.36
 */
public class RandomQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    @SuppressWarnings("unchecked")
    public RandomQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (size == items.length) resize(items.length * 2);
        items[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue underflow");
        int randomIdx = StdRandom.uniform(0, size);
        Item randomItem = items[randomIdx];
        items[randomIdx] = items[size - 1];
        items[size - 1] = null; // avoid loitering
        size--;
        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return randomItem;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        if (size >= 0) System.arraycopy(items, 0, temp, 0, size);
        items = temp;
    }

    /**
     * return a random item, but do not remove (sample with replacement)
     */
    public Item sample() {
        if (isEmpty()) throw new RuntimeException("Queue underflow");
        int randomIdx = StdRandom.uniform(0, size);
        return items[randomIdx];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {

        int index;
        Item[] arrayCopy;

        @SuppressWarnings("unchecked")
        RandomQueueIterator() {
            index = 0;
            arrayCopy = (Item[]) new Object[items.length];
            copyArray();
            shuffleItems();
        }

        public boolean hasNext() {
            return index < size;
        }

        public Item next() {
            return arrayCopy[index++];
        }

        private void copyArray() {
            if (size >= 0) System.arraycopy(items, 0, arrayCopy, 0, size);
        }

        private void shuffleItems() {
            for (int i = 0; i < size; i++) {
                int randomIndex = StdRandom.uniform(0, i + 1);

                //Swap
                Item temp = arrayCopy[i];
                arrayCopy[i] = arrayCopy[randomIndex];
                arrayCopy[randomIndex] = temp;
            }
        }

    }

}
