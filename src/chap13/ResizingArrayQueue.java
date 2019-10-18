package chap13;

import interfaces.Queue;
import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Queue implementation with a resizing array.
 *
 *  This implementation uses a resizing array, which double the underlying array
 *  when it is full and halves the underlying array when it is one-quarter full.
 *  The <em>enqueue</em> and <em>dequeue</em> operations take constant amortized time.
 *  The <em>size</em>, <em>peek</em>, and <em>is-empty</em> operations takes
 *  constant time in the worst case.
 *
 */
public class ResizingArrayQueue<Item> implements Iterable<Item>, Queue<Item> {

    private Item[] q;   // queue elements
    private int n;      // number of elements on queue
    private int first;  // index of first element of queue
    private int last;   // index of next available slot

    public ResizingArrayQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    public void enqueue(Item item) {
        if (n == q.length) resize(2 * q.length);
        q[last++] = item;
        if (last == q.length) last = 0;
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = q[first];
        q[first] = null;   // to avoid loitering
        n--;
        first++;
        if (first == q.length) first = 0;
        if (n > 0 && n == q.length / 4) resize(q.length / 2);
        return item;
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return q[first];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last = n;
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
                Item item = q[(i + first) % q.length];
                i++;
                return item;
            }

        };
    }

    public static void main(String[] args) throws IOException {
        In inputs = ResourceFinder.findResourceInputStream("tobe.txt");
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<>();
        while (!inputs.isEmpty()) {
            String item = inputs.readString();
            if      (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty())  StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on  queue)");
    }

}
