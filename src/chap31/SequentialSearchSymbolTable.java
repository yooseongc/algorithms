package chap31;

import chap13.Queue;
import interfaces.SymbolTable;

public class SequentialSearchSymbolTable<Key, Value> implements SymbolTable<Key, Value> {

    private Node first;
    private int size;

    private class Node {
        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to get() cannot be null");
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) return x.value;
        }
        return null;
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (value == null) {
            delete(key);
            return;
        }
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        }
        first = new Node(key, value, first);
        size++;
    }

    @Override
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to contains() cannot be null");
        return get(key) != null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to delete() cannot be null");
        if (isEmpty()) return;
        if (first.key.equals(key)) {
            first = first.next;
            size--;
            return;
        }
        for (Node node = first; node != null; node = node.next) {
            if (node.next != null && node.next.key.equals(key)) {
                node.next = node.next.next;
                size--;
                return;
            }
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> keys = new Queue<>();
        for (Node node = first; node != null; node = node.next) {
            keys.enqueue(node.key);
        }
        return keys;
    }

}
