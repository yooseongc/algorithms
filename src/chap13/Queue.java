package chap13;

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {

    public Queue() {

    }

    public void enqueue(Item item) {

    }

    public Item dequeue() {
        return null;
    }

    public boolean isEmpty() {
        return true;
    }

    public int size() {
        return 0;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Item next() {
                return null;
            }

        };
    }

}
