package chap13;

import interfaces.Bag;
import utilities.StdOut;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The {@code ResizingArrayBag} class represents a bag (or multiset) of
 *  generic items. It supports insertion and iterating over the
 *  items in arbitrary order.
 *  <p>
 *  This implementation uses a resizing array.
 *  See {@link Bag} for a version that uses a singly linked list.
 *  The <em>add</em> operation takes constant amortized time; the
 *  <em>isEmpty</em>, and <em>size</em> operations
 *  take constant time. Iteration takes time proportional to the number of items.
 */
public class ResizingArrayBag<Item> implements Bag<Item>, Iterable<Item> {

    private Item[] a;   // array of items
    private int n;      // number of elements on bag

    public ResizingArrayBag() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    @Override
    public void add(Item item) {
        if (n == a.length) resize(2 * a.length);
        a[n++] = item;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        a = Arrays.copyOf(a, capacity);
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
        return new Iterator<Item>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < n;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                return a[i++];
            }
        };
    }

    public static void main(String[] args) {

        ResizingArrayBag<String> bag = new ResizingArrayBag<>();
        bag.add("Hello");
        bag.add("World");
        bag.add("how");
        bag.add("are");
        bag.add("you");

        for (String s : bag)
            StdOut.println(s);
    }

}
