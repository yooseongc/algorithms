package chap13;

import interfaces.Stack;
import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic Stack implementations with a fixed-size array.
 *
 * Remark: bare-bones implementation.
 *   Does not do repeated doubling or null out empty array entries to avoid loitering.
 *
 */
public class FixedCapacityStack<Item> implements Iterable<Item>, Stack<Item> {

    private Item[] a;  // holds the items
    private int N;       // number of items in stack

    public FixedCapacityStack(int capacity) {
        a = (Item[]) new Object[capacity];
        N = 0;
    }

    public void push(Item item) {
        if (isFull()) throw new IllegalStateException("Stack is full.");
        a[N++] = item;
    }

    public Item pop() {
        if (isEmpty()) throw new IllegalStateException("Stack is empty.");
        return a[--N];
    }

    public Item peek() {
        if (isEmpty()) throw new IllegalStateException("Stack is empty.");
        return a[N-1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean isFull() {
        return N == a.length;
    }

    public int size() {
        return 0;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    public static void main(String[] args) throws IOException {
        args = new String[] { "5" };
        In inputs = ResourceFinder.findResourceInputStream("tobe.txt");

        int max = Integer.parseInt(args[0]);
        FixedCapacityStack<String> stack = new FixedCapacityStack<>(max);
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

        private int i = N - 1;

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
