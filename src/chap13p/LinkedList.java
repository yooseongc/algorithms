package chap13p;

import utilities.StdOut;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Problem 1.3.19 (remove last node), 1.3.20(remove k-th node), 1.3.21(find by key),
 * 1.3.24 (removeAfter), 1.3.25 (insertAfter), 1.3.26 (remove all by key),
 * 1.3.27 (max), 1.3.28 (max by recursive)
 * @param <Item>
 */
public class LinkedList<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
    }

    private Node createNode(Item item) {
        Node node = new Node();
        node.item = item;
        return node;
    }

    private int size;
    private Node first;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void add(Item item) {
        if (isEmpty()) {
            first = new Node();
            first.item = item;
        } else {
            Node current;
            for(current = first; current.next != null; current = current.next);

            Node newNode = new Node();
            newNode.item = item;
            current.next = newNode;
        }
        size++;
    }

    public void deleteLastNode() {
        if (!isEmpty()) {
            if (size == 1) {
                first = null;
            } else {
                Node current = first;
                for (int i = 0; i < size - 2; i++) {
                    current = current.next;
                }
                current.next = null;
            }

            size--;
        }
    }

    public void delete(int k) {
        if (k > size || isEmpty()) {
            return;
        }

        if (k == 1) {
            first = first.next;
        } else {
            Node current;
            int count = 1;

            for(current = first; current != null; current = current.next) {
                if (count == k - 1 && current.next != null) {
                    current.next = current.next.next;
                    break;
                }
                count++;
            }
        }
        size--;
    }

    public boolean find(String key) {
        if (isEmpty()) {
            return false;
        }

        boolean contains = false;
        Node current;
        for (current = first; current != null; current = current.next) {
            if (current.item.equals(key)) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    public void removeAfter(Node node) {
        if (isEmpty() || node == null) {
            return;
        }

        Node current;

        for(current = first; current != null; current = current.next) {
            if (current.item.equals(node.item)) {
                if (current.next != null) {
                    current.next = current.next.next;
                    size--;
                }
                break;
            }
        }
    }

    public void insertAfter(Node firstNode, Node secondNode) {
        if (isEmpty() || firstNode == null || secondNode == null) {
            return;
        }

        Node current;

        for (current = first; current != null; current = current.next) {
            if (current.item.equals(firstNode.item)) {
                secondNode.next = current.next;
                current.next = secondNode;
                size++;
            }
        }
    }

    public void remove(Item key) {
        if (isEmpty() || key == null) {
            return;
        }

        while (first != null && first.item.equals(key)) {
            first = first.next;
            size--;
        }

        Node current;

        for(current = first; current != null; current = current.next) {
            Node next = current.next;

            while (next != null && next.item.equals(key)) {
                next = next.next;
                size--;
            }

            current.next = next;
        }
    }

    public int max() {
        if (isEmpty()) {
            return 0;
        }

        int maxValue = (Integer) first.item;

        Node current;

        for (current = first.next; current != null; current = current.next) {
            int currentValue = (Integer) current.item;

            if (currentValue > maxValue) {
                maxValue = currentValue;
            }
        }

        return maxValue;
    }

    public int maxRecursive() {
        if (isEmpty()) return 0;
        int currentMaxValue = (Integer) first.item;
        return getMax(first.next, currentMaxValue);
    }

    private int getMax(Node node, int currentMaxValue) {
        if (node == null) return currentMaxValue;
        int currentValue = (Integer) node.item;
        if (currentValue > currentMaxValue) currentMaxValue = currentValue;
        return getMax(node.next, currentMaxValue);
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        Node current = first;

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

        // 1.3.19
        StdOut.println("Problem 1.3.19");
        LinkedList<Integer> linkedList19 = new LinkedList<>();
        linkedList19.add(0);
        linkedList19.add(1);
        linkedList19.add(2);
        linkedList19.add(3);

        StdOut.println("Before removing last node");
        StringJoiner listBeforeRemove = new StringJoiner(" ");
        for (int number : linkedList19) {
            listBeforeRemove.add(String.valueOf(number));
        }
        StdOut.println(listBeforeRemove.toString());
        StdOut.println("Expected: 0 1 2 3");

        linkedList19.deleteLastNode();

        StdOut.println("After removing last node");
        StringJoiner listAfterRemove = new StringJoiner(" ");
        for (int number : linkedList19) {
            listAfterRemove.add(String.valueOf(number));
        }
        StdOut.println(listAfterRemove.toString());
        StdOut.println("Expected: 0 1 2");

        // 1.3.20
        StdOut.println("\n\nProblem 1.3.20");
        LinkedList<Integer> linkedList20 = new LinkedList<>();
        linkedList20.add(0);
        linkedList20.add(1);
        linkedList20.add(2);
        linkedList20.add(3);

        StdOut.println("Before removing second node");
        listBeforeRemove = new StringJoiner(" ");
        for (int number : linkedList20) {
            listBeforeRemove.add(String.valueOf(number));
        }
        StdOut.println(listBeforeRemove.toString());
        StdOut.println("Expected: 0 1 2 3");

        linkedList20.delete(2);

        StdOut.println("\nAfter removing second node");
        listAfterRemove = new StringJoiner(" ");
        for (int number : linkedList20) {
            listAfterRemove.add(String.valueOf(number));
        }

        StdOut.println(listAfterRemove.toString());
        StdOut.println("Expected: 0 2 3");

        // 1.3.21
        StdOut.println("\n\nProblem 1.3.21");
        LinkedList<String> linkedList21 = new LinkedList<>();
        linkedList21.add("A");
        linkedList21.add("B");
        linkedList21.add("C");
        linkedList21.add("D");

        StdOut.println("Find result: " + linkedList21.find("B") + " Expected: true");
        StdOut.println("Find result: " + linkedList21.find("Z") + " Expected: false");


        // 1.3.24
        StdOut.println("\n\nProblem 1.3.24");
        LinkedList<Integer> linkedList24 = new LinkedList<>();
        linkedList24.add(0);
        linkedList24.add(1);
        linkedList24.add(2);
        linkedList24.add(3);
        linkedList24.add(4);
        StdOut.println("Before removing node after node 0");

        listBeforeRemove = new StringJoiner(" ");
        for (int number : linkedList24) {
            listBeforeRemove.add(String.valueOf(number));
        }
        StdOut.println(listBeforeRemove.toString());
        StdOut.println("Expected: 0 1 2 3 4");

        LinkedList<Integer>.Node nodeToBeDeleted = linkedList24.createNode(0);
        linkedList24.removeAfter(nodeToBeDeleted);
        StdOut.println("\nAfter removing node after node 0");

        listAfterRemove = new StringJoiner(" ");
        for (int number : linkedList24) {
            listAfterRemove.add(String.valueOf(number));
        }

        StdOut.println(listAfterRemove.toString());
        StdOut.println("Expected: 0 2 3 4");

        // 1.3.25
        StdOut.println("\n\nProblem 1.3.25");
        LinkedList<Integer> linkedList25 = new LinkedList<>();
        linkedList25.add(0);
        linkedList25.add(1);
        linkedList25.add(2);
        linkedList25.add(3);
        linkedList25.add(4);
        StdOut.println("Before inserting node 99 (after node 2)");

        StringJoiner listBeforeInsert = new StringJoiner(" ");
        for (int number : linkedList25) {
            listBeforeInsert.add(String.valueOf(number));
        }
        StdOut.println(listBeforeInsert.toString());
        StdOut.println("Expected: 0 1 2 3 4");

        LinkedList<Integer>.Node nodeOfReference = linkedList25.createNode(2);
        LinkedList<Integer>.Node nodeToBeInserted = linkedList25.createNode(99);
        linkedList25.insertAfter(nodeOfReference, nodeToBeInserted);
        StdOut.println("\nAfter inserting node 99 (after node 2)");

        StringJoiner listAfterInsert = new StringJoiner(" ");
        for (int number : linkedList25) {
            listAfterInsert.add(String.valueOf(number));
        }

        StdOut.println(listAfterInsert.toString());
        StdOut.println("Expected: 0 1 2 99 3 4");

        // 1.3.26
        StdOut.println("\n\nProblem 1.3.26");
        LinkedList<String> linkedList26 = new LinkedList<>();
        linkedList26.add("Mark");
        linkedList26.add("Bill");
        linkedList26.add("Elon");
        linkedList26.add("Rene");
        linkedList26.add("Mark");
        linkedList26.add("Mark");
        linkedList26.add("Mark");
        linkedList26.add("Elon");

        StdOut.println("Before removing Mark");

        listBeforeRemove = new StringJoiner(" ");
        for (String name : linkedList26) {
            listBeforeRemove.add(name);
        }

        StdOut.println(listBeforeRemove.toString());
        StdOut.println("Expected: Mark Bill Elon Rene Mark Mark Mark Elon");

        String itemToBeRemoved = "Mark";
        linkedList26.remove(itemToBeRemoved);

        StdOut.println("\nAfter removing Mark");

        listAfterRemove = new StringJoiner(" ");
        for (String name : linkedList26) {
            listAfterRemove.add(name);
        }

        StdOut.println(listAfterRemove.toString());
        StdOut.println("Expected: Bill Elon Rene Elon");

        // 1.3.27
        StdOut.println("\n\nProblem 1.3.27");
        LinkedList<Integer> linkedList27 = new LinkedList<>();
        linkedList27.add(3);
        linkedList27.add(91);
        linkedList27.add(2);
        linkedList27.add(9);

        int maxValue = linkedList27.max();
        StdOut.println("Max value: " + maxValue);
        StdOut.println("Expected: 91");

        // 1.3.28
        StdOut.println("\n\nProblem 1.3.28");
        LinkedList<Integer> linkedList28 = new LinkedList<>();
        linkedList28.add(3);
        linkedList28.add(91);
        linkedList28.add(2);
        linkedList28.add(9);

        int maxValue2 = linkedList28.maxRecursive();
        StdOut.println("Max value: " + maxValue2);
        StdOut.println("Expected: 91");
    }

}
