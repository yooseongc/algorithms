package chap13p;

import utilities.StdOut;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * reverse the linked list.
 */
public class Problem30<Item> implements Iterable<Item> {

    private class Node<Item> {
        Item item;
        Node<Item> next;
    }

    private int size;
    private Node<Item> first;

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    public void add(Item item) {
        if (isEmpty()) {
            first = new Node<>();
            first.item = item;
        } else {
            Node<Item> current;
            for (current = first; current.next != null; current = current.next);
            Node<Item> newNode = new Node<>();
            newNode.item = item;
            current.next = newNode;
        }
        size++;
    }

    public void delete(int k) {
        if (k > size || isEmpty()) {
            return;
        }
        if (k == 1) {
            first = first.next;
        } else {
            Node<Item> current;
            int count = 1;
            for (current = first; current != null; current = current.next) {
                if (count == k - 1 && current.next != null) {
                    current.next = current.next.next;
                    break;
                }
                count++;
            }
        }
        size--;
    }

    /**
     * First Implementation
     * @return first element of reversed list.
     */
    public Node<Item> reverse() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            return first;
        }
        Node<Item> old = first;
        Node<Item> current = first.next;
        Node<Item> newNode = first.next.next;

        first.next = null;
        first = current;
        current.next = old;

        while (newNode != null) {
            old = current;
            current = newNode;
            newNode = newNode.next;

            current.next = old;
            first = current;
        }

        return first;
    }

    /**
     * Improved iteration implementation
     * @return first element of reversed list.
     */
    public Node<Item> reverse2() {
        Node<Item> reverse = null;
        while (first != null) {
            Node<Item> second = first.next;
            first.next = reverse;
            reverse = first;
            first = second;
        }
        first = reverse;
        return reverse;
    }

    /**
     * recursive implementation
     * @return first element of reversed list.
     */
    public Node<Item> reverse3() {
        first = reverse3Impl(first);
        return first;
    }

    private Node<Item> reverse3Impl(Node<Item> first) {
        if (first == null) {
            return null;
        }
        if (first.next == null) {
            return first;
        }
        Node<Item> second = first.next;
        Node<Item> rest = reverse3Impl(second);
        second.next = first;
        first.next = null;
        return rest;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    public static void main(String[] args) {
        Problem30<Integer> linkedList = new Problem30<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);

        linkedList.reverse3();
        StringJoiner listItems = new StringJoiner(" ");
        for (int item : linkedList) {
            listItems.add(String.valueOf(item));
        }
        StdOut.println("Reverse list items: " + listItems.toString());
        StdOut.println("Expected: 4 3 2 1");

        linkedList.reverse2();
        listItems = new StringJoiner(" ");
        for (int item : linkedList) {
            listItems.add(String.valueOf(item));
        }
        StdOut.println("Reverse list items: " + listItems.toString());
        StdOut.println("Expected: 1 2 3 4");
    }

}
