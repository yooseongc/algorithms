package interfaces;

public interface Stack<Item> {

    void push(Item item);

    Item pop();

    Item peek();

    boolean isEmpty();

    int size();

}
