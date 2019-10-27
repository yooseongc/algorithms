package chap13p;

import utilities.StdOut;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * problem 1.3.33
 */
public class ResizingArrayDeque<Item> implements Iterable<Item> {

    private int first;
    private int last;
    private Item[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public ResizingArrayDeque() {
        array = (Item[]) new Object[1];
        size = 0;
        first = -1;
        last = -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void pushLeft(Item item) {
        if (size == array.length) resize(size * 2);
        if (first == 0) moveArrayRight();
        if (isEmpty()) {
            first = 0;
            last = 0;
            array[first] = item;
        } else {
            array[first - 1] = item;
            first = first - 1;
        }
        size++;
    }

    public void pushRight(Item item) {
        if (size == array.length) resize(size * 2);
        if (last == array.length - 1) moveArrayLeft();
        if (isEmpty()) {
            first = 0;
            last = 0;
            array[last] = item;
        } else {
            array[last + 1] = item;
            last = last + 1;
        }
        size++;
    }

    public Item popLeft() {
        if (isEmpty()) throw new RuntimeException("deque underflow.");
        Item item = array[first];
        array[first] = null; // avoid loitering
        size--;
        if (isEmpty()) {
            first = -1;
            last = -1;
        } else {
            first = first + 1;
        }
        if (size == array.length / 4) resize(array.length / 2);
        return item;
    }

    public Item popRight() {
        if (isEmpty()) throw new RuntimeException("deque underflow.");
        Item item = array[last];
        array[last] = null; // avoid loitering
        size--;
        if (isEmpty()) {
            first = -1;
            last = -1;
        } else {
            last = last -1;
        }
        if (size == array.length / 4) resize(array.length / 2);
        return item;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = first; i <= last; i++) {
            newArray[j++] = array[i];
        }
        first = 0;
        last = size - 1;
        array = newArray;
    }

    private void moveArrayRight() {
        for (int i = last; i >= first; i--) {
            array[i + 1] = array[i];
        }
        first++;
        last++;
    }

    private void moveArrayLeft() {
        for (int i = first; i <= last; i++) {
            array[i - 1] = array[i];
        }
        first--;
        last--;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        int index = first;

        @Override
        public boolean hasNext() {
            return index <= last && last != -1;
        }

        @Override
        public Item next() {
            return array[index++];
        }
    }

    public static void main(String[] args) {

        StdOut.println("Test Push Left");
        ResizingArrayDeque<String> deque = new ResizingArrayDeque<>();
        deque.pushLeft("a"); // a
        deque.pushLeft("b"); // b -> a
        deque.pushLeft("c"); // c -> b -> a
        StringJoiner dequeItems = new StringJoiner(" ");
        for (String item : deque) {
            dequeItems.add(item);
        }
        StdOut.println("Deque items: " + dequeItems.toString());
        StdOut.println("Expected: c b a");

        StdOut.println("\nTest Push Right");
        deque = new ResizingArrayDeque<>();
        deque.pushRight("a");  // a
        deque.pushRight("b");  // a -> b
        deque.pushRight("c");  // a -> b -> c

        dequeItems = new StringJoiner(" ");
        for (String item : deque) {
            dequeItems.add(item);
        }

        StdOut.println("Deque items: " + dequeItems.toString());
        StdOut.println("Expected: a b c");

        StdOut.println("\nTest Pop Left");

        deque = new ResizingArrayDeque<>();
        deque.pushRight("a");  // a
        deque.pushRight("b");  // a -> b
        deque.pushRight("c");  // a -> b -> c
        deque.popLeft();       // b -> c
        deque.popLeft();       // c

        dequeItems = new StringJoiner(" ");
        for (String item : deque) {
            dequeItems.add(item);
        }

        StdOut.println("Deque items: " + dequeItems.toString());
        StdOut.println("Expected: c");

        StdOut.println("\nTest Pop Right");

        deque = new ResizingArrayDeque<>();
        deque.pushRight("a");  // a
        deque.pushRight("b");  // a -> b
        deque.pushRight("c");  // a -> b -> c
        deque.popRight();            // a -> b
        deque.popRight();            // a

        dequeItems = new StringJoiner(" ");
        for (String item : deque) {
            dequeItems.add(item);
        }

        StdOut.println("Deque items: " + dequeItems.toString());
        StdOut.println("Expected: a");

    }

}
