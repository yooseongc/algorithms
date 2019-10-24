package chap13p;

import utilities.StdOut;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * problem 1.3.31 doubly linked list using DoubleNode
 */
public class DoubleLinkedList<Item> implements Iterable<Item> {

    private class DoubleNode {
        Item item;
        DoubleNode prev;
        DoubleNode next;
    }

    private int size;
    private DoubleNode first;
    private DoubleNode last;

    public boolean isEmpty() { return size == 0; }

    public int size() { return size; }

    public void insertAtFirst(Item item) {
        DoubleNode oldFirst = first;
        first = new DoubleNode();
        first.item = item;
        first.next = oldFirst;

        if (oldFirst != null) {
            oldFirst.prev = first;
        }
        if (isEmpty()) {
            last = first;
        }
        size++;
    }

    public void insertAtLast(Item item) {
        DoubleNode oldLast = last;
        last = new DoubleNode();
        last.item = item;
        last.prev = oldLast;

        if (oldLast != null) {
            oldLast.next = last;
        }
        if (isEmpty()) {
            first = last;
        }
        size++;
    }

    public void insertBeforeNode(Item beforeItem, Item item) {
        if (isEmpty()) return;

        DoubleNode currentNode;
        for (currentNode = first; currentNode != null; currentNode = currentNode.next) {
            if (currentNode.item.equals(beforeItem)) break;
        }
        if (currentNode != null) {
            DoubleNode newNode = new DoubleNode();
            newNode.item = item;

            DoubleNode prevNode = currentNode.prev;
            currentNode.prev = newNode;
            newNode.next = currentNode;
            newNode.prev = prevNode;

            if (newNode.prev == null) {
                first = newNode;
            } else {
                newNode.prev.next = newNode;
            }
            size++;
        }
    }

    public void insertAfterNode(Item afterItem, Item item) {
        if (isEmpty()) return;

        DoubleNode currentNode;
        for (currentNode = first; currentNode != null; currentNode = currentNode.next) {
            if (currentNode.item.equals(afterItem)) break;
        }
        if (currentNode != null) {
            DoubleNode newNode = new DoubleNode();
            newNode.item = item;

            DoubleNode nextNode = currentNode.next;
            currentNode.next = newNode;
            newNode.prev = currentNode;
            newNode.next = nextNode;

            if (newNode.next == null) {
                last = newNode;
            } else {
                newNode.next.prev = newNode;
            }
            size++;
        }
    }

    public Item removeFromFirst() {
        if (isEmpty()) return null;

        Item item = first.item;
        if (first.next != null) {
            first.next.prev = null;
        } else {
            last = null;
        }
        first = first.next;
        size--;

        return item;
    }

    public Item removeFromEnd() {
        if (isEmpty()) return null;

        Item item = last.item;
        if (last.prev != null) {
            last.prev.next = null;
        } else {
            first = null;
        }
        last = last.prev;
        size--;

        return item;
    }

    public Item remove(int nodeIndex) {
        if (isEmpty() || nodeIndex <= 0 || nodeIndex > size()) return null;

        boolean startFromBeginning = nodeIndex <= size() / 2;
        int index = startFromBeginning ? 1 : size();
        DoubleNode currentNode = startFromBeginning ? first : last;
        while (currentNode != null) {
            if (nodeIndex == index) break;
            if (startFromBeginning) {
                index++;
            } else {
                index--;
            }
            currentNode = startFromBeginning ? currentNode.next : currentNode.prev;
        }

        @SuppressWarnings("ConstantConditions")
        Item item = currentNode.item;

        DoubleNode prevNode = currentNode.prev;
        DoubleNode nextNode = currentNode.next;
        if (prevNode != null) {
            prevNode.next = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        }
        if (currentNode == first) {
            first = nextNode;
        }
        if (currentNode == last) {
            last = prevNode;
        }
        size--;

        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DoublyLinkedListIterator();
    }

    private class DoublyLinkedListIterator implements Iterator<Item> {

        int index = 0;
        DoubleNode currentNode = first;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Item next() {
            Item item = currentNode.item;
            currentNode = currentNode.next;

            index++;

            return item;
        }
    }

    public static void main(String[] args) {
        DoubleLinkedList<Integer> doublyLinkedList = new DoubleLinkedList<>();
        doublyLinkedList.insertAtFirst(10);
        doublyLinkedList.insertAtFirst(30);
        doublyLinkedList.insertAtLast(999);

        StringJoiner doublyLinkedListItems = new StringJoiner(" ");
        for (int item : doublyLinkedList) {
            doublyLinkedListItems.add(String.valueOf(item));
        }

        StdOut.println("Doubly linked list items after initial insert: " + doublyLinkedListItems.toString());
        StdOut.println("Expected: 30 10 999");

        doublyLinkedList.insertBeforeNode(9999, 998);
        doublyLinkedList.insertBeforeNode(999, 997);

        doublyLinkedListItems = new StringJoiner(" ");
        for (int item : doublyLinkedList) {
            doublyLinkedListItems.add(String.valueOf(item));
        }

        StdOut.println("\nDoubly linked list items after insert before 999: " + doublyLinkedListItems.toString());
        StdOut.println("Expected: 30 10 997 999");

        doublyLinkedList.insertAfterNode(10, 11);

        doublyLinkedListItems = new StringJoiner(" ");
        for (int item : doublyLinkedList) {
            doublyLinkedListItems.add(String.valueOf(item));
        }

        StdOut.println("\nDoubly linked list items after insert after 10: " + doublyLinkedListItems.toString());
        StdOut.println("Expected: 30 10 11 997 999");

        doublyLinkedList.removeFromFirst();
        doublyLinkedList.removeFromEnd();

        doublyLinkedList.remove(2);

        doublyLinkedListItems = new StringJoiner(" ");
        for (int item : doublyLinkedList) {
            doublyLinkedListItems.add(String.valueOf(item));
        }

        StdOut.println("\nDoubly linked list items after deletions: " + doublyLinkedListItems.toString());
        StdOut.println("Expected: 10 997");
    }

}
