package chap13;

import interfaces.Stack;
import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic Stack implementations with a resizing array.
 *
 * This implementation uses a resizing array, which double the underlying array
 *   when it is full and halves the underlying array when it is one-quarter full.
 *
 * The <em>push</em> and <em>pop</em> operations take constant amortized time.
 * The <em>size</em>, <em>peek</em>, and <em>is-empty</em> operations takes
 *   constant time in the worst case.
 */
public class ResizingArrayStack<Item> implements Iterable<Item>, Stack<Item> {

    private Item[] a;  // holds the items
    private int n;       // number of items in stack

    public ResizingArrayStack() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    public void push(Item item) {
        if (n == a.length) resize(2 * a.length); // double size of array if necessary
        a[n++] = item;
    }

    public Item pop() {
        if (isEmpty()) throw new IllegalStateException("Stack is empty.");
        Item item = a[n-1];
        a[n-1] = null;  // to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return item;
    }

    public Item peek() {
        if (isEmpty()) throw new IllegalStateException("Stack is empty.");
        return a[n-1];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean isFull() {
        return n == a.length;
    }

    public int size() {
        return n;
    }

    /**
     * resize the underlying array holding the elements
     */
    private void resize(int capacity) {
        assert capacity >= n;
        // System.out.printf("resize %d --> %d\n", a.length, capacity);
        a = Arrays.copyOf(a, capacity);
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
        return new ReverseArrayIterator();
    }

    public static void main(String[] args) throws IOException {
        In inputs = ResourceFinder.findResourceInputStream("tobe.txt");
        ResizingArrayStack<String> stack = new ResizingArrayStack<>();
        while (!inputs.isEmpty()) {
            String item = inputs.readString();
            if      (!item.equals("-")) stack.push(item);
            else if (stack.isEmpty()) StdOut.println("BAD INPUT");
            else                      StdOut.print(stack.pop() + " ");
        }
        StdOut.println();

        // print what is left on the stack
        StdOut.print("Left on stack: ");
        for (String s : stack) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }

    private class ReverseArrayIterator implements Iterator<Item> {

        private int i = n - 1;

        @Override
        public boolean hasNext() {
            return i >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }
}
