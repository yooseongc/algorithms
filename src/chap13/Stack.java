package chap13;

import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {

    public Stack() {

    }

    public void push(Item item) {

    }

    public Item pop() {
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
