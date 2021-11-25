package chap6;

public interface PageSymbolTableInterface<Key, Value> {

    int pageSize();

    int keysSize(boolean countSentinel);

    void setKeysSize(int size);

    void incrementKeysSize();

    void decrementKeysSize();

    boolean isEmpty();

    boolean containsSentinel();

    void setContainsSentinel(boolean containsSentinel);

    void open();

    void close(boolean verbose);

    boolean isExternal();

    boolean contains(Key key);

    Value get(Key key);

    PageSymbolTableInterface<Key, Value> getParentPage();

    void setParentPage(PageSymbolTableInterface<Key, Value> parentPage);

    PageSymbolTableInterface<Key, Value> getChildPage(Key key);

    void add(Key key, Value value);

    void addPage(PageSymbolTableInterface<Key, Value> page);

    void addAllKeysFromPage(PageSymbolTableInterface<Key, Value> page);

    Key min();

    Key max();

    Key floor(Key key);

    Key ceiling(Key key);

    Key select(int index);

    int rank(Key key);

    void delete(Key key);

    void deletePageWithKey(Key key);

    void deleteMin();

    void deleteMax();

    PageSymbolTableInterface<Key, Value> next(Key key);

    boolean isFull();

    PageSymbolTableInterface<Key, Value> split();

    Iterable<Key> keys();

    int maxNumberOfNodes();

}
