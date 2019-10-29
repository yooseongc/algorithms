package chap13p;

import utilities.StdOut;

/**
 * Problem 1.3.44
 */
public class TextEditorBuffer<Item extends Character> {

    private class Node {
        Item item;
        Node next;
    }

    private int sizeLeft;
    private int sizeRight;
    private Node firstLeftStack;
    private Node firstRightStack;

    public TextEditorBuffer() {
        sizeLeft = 0;
        sizeRight = 0;
        firstLeftStack = null;
        firstRightStack = null;
    }

    /**
     * insert item on cursor pointer
     */
    public void insert(Item item) {
        Node oldFirstLeft = firstLeftStack;
        firstLeftStack = new Node();
        firstLeftStack.item = item;
        firstLeftStack.next = oldFirstLeft;
        sizeLeft++;
    }

    private void insertOnRightStack(Item item) {
        Node oldFirstRight = firstRightStack;
        firstRightStack = new Node();
        firstRightStack.item = item;
        firstRightStack.next = oldFirstRight;
        sizeRight++;
    }

    /**
     * return the item on cursor
     */
    public Item get() {
        if (sizeRight == 0) return null;
        return firstRightStack.item;
    }

    public Item delete() {
        if (sizeRight == 0) return null;
        Item item = firstRightStack.item;
        firstRightStack = firstRightStack.next;
        sizeRight--;
        return item;
    }

    public void left(int k) {
        int count = 0;
        while (count < k && sizeLeft > 0) {
            Item item = firstLeftStack.item;
            insertOnRightStack(item);
            firstLeftStack = firstLeftStack.next;
            sizeLeft--;
            count++;
        }
    }

    public void right(int k) {
        int count = 0;
        while (count < k && sizeRight > 0) {
            Item item = firstRightStack.item;
            insert(item);
            firstRightStack = firstRightStack.next;
            sizeRight--;
            count++;
        }
    }

    public int size() {
        return sizeLeft + sizeRight;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Left Stack: ");
        for(Node current = firstLeftStack; current != null; current = current.next) {
            stringBuilder.append(current.item).append(" ");
        }

        stringBuilder.append("\nRight Stack: ");
        for(Node current = firstRightStack; current != null; current = current.next) {
            stringBuilder.append(current.item).append(" ");
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        TextEditorBuffer<Character> textEditorBuffer = new TextEditorBuffer<>();
        StdOut.println("Test insert : 'Rene'");
        textEditorBuffer.insert('R');
        textEditorBuffer.insert('e');
        textEditorBuffer.insert('n');
        textEditorBuffer.insert('e');
        StdOut.println(textEditorBuffer);
        StdOut.println("Expected:\n" +
                "Left Stack: e n e R \n" +
                "Right Stack: ");

        StdOut.println("\nTest left");
        textEditorBuffer.left(3);
        StdOut.println(textEditorBuffer);
        StdOut.println("Expected:\n" +
                "Left Stack: R \n" +
                "Right Stack: e n e");

        StdOut.println("\nTest right");
        textEditorBuffer.right(2);

        StdOut.println(textEditorBuffer);
        StdOut.println("Expected:\n" +
                "Left Stack: n e R \n" +
                "Right Stack: e");

        StdOut.println("\nTest get");
        StdOut.println(textEditorBuffer.get());
        StdOut.println(textEditorBuffer.get());
        StdOut.println("Expected:\ne\ne");

        StdOut.println("\nTest delete");
        StdOut.println(textEditorBuffer.delete());
        StdOut.println(textEditorBuffer);
        StdOut.println("Expected:\n" +
                "e\n" +
                "Left Stack: n e R \n" +
                "Right Stack: ");
    }

}
