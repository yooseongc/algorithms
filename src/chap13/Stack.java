package chap13;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  A generic stack, implemented using a singly linked list.
 *  Each stack element is of type Item.
 *
 *  This version uses a static nested class Node (to save 8 bytes per
 *  Node), whereas the version in the textbook uses a non-static nested
 *  class (for simplicity).
 */
public class Stack<Item> implements Iterable<Item>, interfaces.Stack<Item> {

    private Node<Item> first;    // top of stack
    private int n;               // size of the stack

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;

        Node() { }

        Node(Node<Item> x) {
            item = x.item;
            next = x.next;
        }
    }

    // It should create an empty stack.
    public Stack() {
        first = null;
        n = 0;
    }

    public Stack(Stack<Item> stack) {
        if (stack.first != null) {
            first = new Node<>(stack.first);
            n++;
            for (Node<Item> x = first; x.next != null; x = x.next) {
                x.next = new Node<>(x.next);
                n++;
            }
        }
    }


    public void push(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        n = n + 1;
    }

    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;
        first = first.next;
        n = n - 1;
        return item;
    }

    /**
     * Returns (but does not remove) the item most recently added to this stack.
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator<>(first);
    }

    private static class ListIterator<Item> implements Iterator<Item> {

        private Node<Item> current;

        ListIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    public static void main(String[] args) {
        int[] values = new int[] { 1, 2, 3, 4, 5};
        Stack<Integer> stack = new Stack<>();
        for (int value : values) {
            stack.push(value); // 1, 2, 3, 4, 5 순서대로 들어갈거야
        }

        System.out.println("Stack : " + stack);

        // top 5 4 3 2 1 bottom
        int[] reversed = new int[values.length];
        int count = 0;
        for (int value : stack) {
            reversed[count++] = value;   // reversed = [ 5, 4, 3, 2, 1 ]
        }

        // check
        int totalN = values.length;
        for (int i = 0; i < totalN; i++) {
            assert values[i] == reversed[totalN - i - 1]; // check LIFO activates correctly.
        }
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(reversed));

    }

}


