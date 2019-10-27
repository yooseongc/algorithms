package chap13p;

import utilities.StdOut;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * problem 1.3.33
 */
public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first;
    private Node last;
    private int size;

    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void pushLeft(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.prev = first;
        } else {
            last = first;
        }
        size++;
    }

    public void pushRight(Item item) {
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

    public Item popLeft() {
        if (isEmpty()) throw new RuntimeException("Deque underflow.");
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        size--;
        return item;
    }

    public Item popRight() {
        if (isEmpty()) throw new RuntimeException("Deque underflow.");
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        int index = 0;
        Node current = first;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Item next() {
            index++;
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {

        StdOut.println("Test Push Left");
        Deque<String> deque = new Deque<>();
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
        deque = new Deque<>();
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

        deque = new Deque<>();
        deque.pushRight("a");  // a
        deque.pushRight("b");  // a -> b
        deque.pushRight("c");  // a -> b -> c
        deque.popLeft();             // b -> c
        deque.popLeft();             // c

        dequeItems = new StringJoiner(" ");
        for (String item : deque) {
            dequeItems.add(item);
        }

        StdOut.println("Deque items: " + dequeItems.toString());
        StdOut.println("Expected: c");

        StdOut.println("\nTest Pop Right");

        deque = new Deque<>();
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
