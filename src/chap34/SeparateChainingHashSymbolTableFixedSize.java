package chap34;

import chap13.Queue;
import interfaces.SymbolTable;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class SeparateChainingHashSymbolTableFixedSize<Key, Value> implements SymbolTable<Key, Value> {

    class SequentialSearchSymbolTable<Key, Value> implements SymbolTable<Key, Value> {

        private class Node {
            Key key;
            Value value;
            SequentialSearchSymbolTable.Node next;

            public Node(Key key, Value value, Node next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }
        }

        private Node first;
        protected int size;

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean contains(Key key) {
            return get(key) != null;
        }

        public Value get(Key key) {
            for (Node node = first; node != null; node = node.next) {
                if (key.equals(node.key)) return node.value;
            }
            return null;
        }

        public void put(Key key, Value value) {
            for (Node node = first; node != null; node = node.next) {
                if (key.equals(node.key)) {
                    node.value = value;
                    return;
                }
            }
            first = new Node(key, value, first);
            size++;
        }

        public void delete(Key key) {
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

        public Iterable<Key> keys() {
            Queue<Key> keys = new Queue<>();
            for (Node node = first; node != null; node = node.next) {
                keys.enqueue(node.key);
            }
            return keys;
        }

    }

    private int size;
    private int keysSize;
    SequentialSearchSymbolTable[] symbolTable;

    private final static int DEFAULT_HASH_TABLE_SIZE = 997;

    public SeparateChainingHashSymbolTableFixedSize() {
        this(DEFAULT_HASH_TABLE_SIZE);
    }

    public SeparateChainingHashSymbolTableFixedSize(int size) {
        this.size = size;
        symbolTable = new SequentialSearchSymbolTable[size];
        for (int i = 0; i < size; i++) {
            symbolTable[i] = new SequentialSearchSymbolTable();
        }
    }

    public int size() {
        return keysSize;
    }

    public boolean isEmpty() {
        return keysSize == 0;
    }

    private int hash(Key key) {
        int hash = key.hashCode() & 0x7fffffff;
        hash = (31 * hash) & 0x7fffffff;
        return hash % size;
    }

    @Override
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to get() cannot be null");
        return (Value) symbolTable[hash(key)].get(key);
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (value == null) {
            delete(key);
            return;
        }
        int hashIndex = hash(key);
        int currentSize = symbolTable[hashIndex].size;
        symbolTable[hashIndex].put(key, value);
        if (currentSize < symbolTable[hashIndex].size) {
            keysSize++;
        }
    }

    @Override
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to contains() cannot be null");
        return get(key) != null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to delete() cannot be null");
        if (isEmpty() || !contains(key)) return;
        symbolTable[hash(key)].delete(key);
        keysSize--;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> keys = new Queue<>();
        for (SequentialSearchSymbolTable<Key, Value> sequentialSearchSymbolTable : symbolTable) {
            for (Key key : sequentialSearchSymbolTable.keys()) {
                keys.enqueue(key);
            }
        }
        if (!keys.isEmpty() && keys.peek() instanceof Comparable) {
            Key[] keysToBeSorted = (Key[]) new Comparable[keys.size()];
            for(int i = 0; i < keysToBeSorted.length; i++) {
                keysToBeSorted[i] = keys.dequeue();
            }
            Arrays.sort(keysToBeSorted);
            for(Key key : keysToBeSorted) {
                keys.enqueue(key);
            }
        }
        return keys;
    }

}
