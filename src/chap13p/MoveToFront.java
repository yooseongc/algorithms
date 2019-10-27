package chap13p;

import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Problem 1.3.40
 */
public class MoveToFront<Item> implements Iterable<Item> {

    private LinkedList<Item> linkedList;
    private Set<Item> existingCharactersHashSet;

    public MoveToFront() {
        linkedList = new LinkedList<>();
        existingCharactersHashSet = new HashSet<>();
    }

    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    public int size() {
        return linkedList.size();
    }

    public void insert(Item item) {
        if (existingCharactersHashSet.contains(item)) {
            delete(item);
        }
        linkedList.addAtFront(item);
        existingCharactersHashSet.add(item);
    }

    private void delete(Item item) {
        if (isEmpty()) {
            return;
        }
        linkedList.remove(item);
    }

    @Override
    public Iterator<Item> iterator() {
        return linkedList.iterator();
    }

    public static void main(String[] args) {

        In inputs = ResourceFinder.generateInputStream("abcdcdz");
        MoveToFront<Character> moveToFront = new MoveToFront<>();
        while (inputs.hasNextChar()) {
            moveToFront.insert(inputs.readChar());
        }
        StringJoiner list = new StringJoiner(" ");
        for (char character: moveToFront) {
            list.add(String.valueOf(character));
        }
        StdOut.println("Characters: " + list.toString());
        StdOut.println("Expected: z d c b a");
    }

}
