package chap6;

public interface PageInterface<Key> {

    void close();

    PageInterface<Key> getPage(Key key);

    void add(Key key);

    void add(PageInterface<Key> page);

    boolean isExternal();

    boolean contains(Key key);

    PageInterface<Key> next(Key key);

    boolean isFull();

    PageInterface<Key> split();

    Iterable<Key> keys();

    int getMaxNumberOfNodes();

    void setVerbose(boolean verbose);

}
