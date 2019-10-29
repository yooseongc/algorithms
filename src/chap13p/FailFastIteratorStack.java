package chap13p;

import utilities.StdOut;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Problem 1.3.50 Fail-fast iterator
 *  immediately throw a java.util.ConcurrentModificationException
 *  if the client modifies the collection (via push() or pop()) during iteration.
 *
 *  Solution: Maintain a counter that counts the number of push() and pop() operations.
 *  When creating an iterator, store this value as an iterator instance variable.
 *  Before each call to hasNext() and next(), check that this value has not changed
 *    since construction of the iterator; if it has, throw an exception.
 */
public class FailFastIteratorStack<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
    }

    private Node first;
    private int size;
    private int operationsCounter;

    public FailFastIteratorStack() {
        first = null;
        size = 0;
        operationsCounter = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void push(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        operationsCounter++;
        size++;
    }

    public Item pop() {
        if (isEmpty()) throw new RuntimeException("Stack underflow.");
        Item item = first.item;
        first = first.next;
        size--;
        operationsCounter++;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new FailFastIteratorStackIterator();
    }

    private class FailFastIteratorStackIterator implements Iterator<Item> {

        int currentOperationsCounter;
        Node current;

        FailFastIteratorStackIterator() {
            current = first;
            currentOperationsCounter = operationsCounter;
        }

        @Override
        public boolean hasNext() {
            if (currentOperationsCounter != operationsCounter) {
                throw new ConcurrentModificationException();
            }
            return current != null;
        }

        @Override
        public Item next() {
            if (currentOperationsCounter != operationsCounter) {
                throw new ConcurrentModificationException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    public static void main(String[] args) {
        StdOut.print("Expected: a java.util.ConcurrentModificationException to be thrown\n");
        FailFastIteratorStack<String> failFastIteratorStack = new FailFastIteratorStack<>();
        failFastIteratorStack.push("a");
        failFastIteratorStack.push("b");
        failFastIteratorStack.push("c");
        failFastIteratorStack.push("d");
        try {
            for (String string : failFastIteratorStack) {
                StdOut.print("Iterating at item " + string + " ");
                failFastIteratorStack.push("z");
            }
        } catch (Exception e) {
            assert e instanceof ConcurrentModificationException;
            e.printStackTrace();
        }
    }

}
