package chap13p;

import utilities.StdOut;
import utilities.StdRandom;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * problem 1.3.34
 */
public class RandomBag<Item> implements Iterable<Item> {

    private Item[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public RandomBag() {
        array = (Item[]) new Object[1];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void add(Item item) {
        if (size == array.length) resize(array.length * 2);
        array[size++] = item;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomBagIterator();
    }

    private class RandomBagIterator implements Iterator<Item> {

        private int index;
        private Item[] arrayCopy;

        @SuppressWarnings("unchecked")
        RandomBagIterator() {
            index = 0;
            arrayCopy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                arrayCopy[i] = array[i];
            }
            sortArrayCopy();
        }

        private void sortArrayCopy() {
            for (int i = 0; i < size; i++) {
                int randomIdx = StdRandom.uniform(0, size - 1);
                // swap
                Item temp = arrayCopy[i];
                arrayCopy[i] = arrayCopy[randomIdx];
                arrayCopy[randomIdx] = temp;
            }
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Item next() {
            return arrayCopy[index++];
        }

    }

    public static void main(String[] args) {
        RandomBag<Integer> randomBag = new RandomBag<>();
        randomBag.add(1);
        randomBag.add(2);
        randomBag.add(3);
        randomBag.add(4);
        randomBag.add(5);
        randomBag.add(6);
        randomBag.add(7);
        randomBag.add(8);

        StdOut.print("Random bag items: ");

        StringJoiner randomBagItems = new StringJoiner(" ");
        for (int item : randomBag) {
            randomBagItems.add(String.valueOf(item));
        }

        StdOut.println(randomBagItems.toString());
    }

}
