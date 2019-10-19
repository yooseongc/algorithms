package chap13;

import interfaces.Stack;
import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayStackWithReflection<Item> implements Stack<Item>, Iterable<Item> {

    private Class<Item[]> itemArrayClass;
    private Item[] array;
    private int n = 0;

    public ResizingArrayStackWithReflection(Class<Item[]> itemArrayClass) {
        this.itemArrayClass = itemArrayClass;
        array = itemArrayClass.cast(Array.newInstance(itemArrayClass.getComponentType(), 1));
    }

    @Override
    public void push(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add null item");
        if (n == array.length) resize(array.length * 2);
        array[n++] = item;
    }

    @Override
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = array[--n];
        array[n] = null;
        if ((n > 0) && n <= array.length / 4) resize(array.length / 2);
        return item;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] newArray = itemArrayClass.cast(Array.newInstance(itemArrayClass.getComponentType(), capacity));
        if (n >= 0) System.arraycopy(array, 0, newArray, 0, n);
        array = newArray;
    }

    @Override
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return array[n-1];
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {

        private int i = n-1;

        @Override
        public boolean hasNext() {
            return i >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return array[i--];
        }

    }

    /**
     * Test client (copied from ResizingArrayStack).
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws IOException {
        In inputs = ResourceFinder.findResourceInputStream("tobe.txt");
        ResizingArrayStackWithReflection<String> s = new ResizingArrayStackWithReflection<>(String[].class);
        while (!inputs.isEmpty()) {
            String item = inputs.readString();
            if (!item.equals("-")) s.push(item);
            else if (!s.isEmpty()) StdOut.print(s.pop() + " ");
        }
        StdOut.println("(" + s.size() + " left on stack)");
    }

}
