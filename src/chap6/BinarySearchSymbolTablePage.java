package chap6;

import chap31.BinarySearchSymbolTable;
import chap35.HashSet;
import utilities.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class BinarySearchSymbolTablePage <Key extends Comparable<Key>, Value> implements PageSymbolTableInterface<Key, Value> {

    private class PageValue {
        private PageSymbolTableInterface<Key, Value> childPage;
        private Value value;
        PageValue(PageSymbolTableInterface<Key, Value> childPage, Value value) {
            this.childPage = childPage;
            this.value = value;
        }
    }

    private PageSymbolTableInterface<Key, Value> parentPage;
    private BinarySearchSymbolTable<Key, PageValue> binarySearchSymbolTable;
    private boolean isOpen;
    private boolean isExternal;
    private int size;
    private boolean containsSentinel;

    private HashSet<PageSymbolTableInterface> pagesInMemory;
    private final static int MAX_NUMBER_OF_NODES = 4;

    BinarySearchSymbolTablePage(boolean bottom, HashSet<PageSymbolTableInterface> pagesInMemory) {
        binarySearchSymbolTable = new BinarySearchSymbolTable<>();
        this.pagesInMemory = pagesInMemory;
        isExternal = bottom;
        open();
    }

    @Override
    public int pageSize() {
        return binarySearchSymbolTable.size();
    }

    @Override
    public int keysSize(boolean countSentinel) {
        int totalSize = size;
        if (containsSentinel() && !countSentinel) {
            totalSize--;
        }
        return totalSize;
    }

    @Override
    public void setKeysSize(int size) {
        this.size = size;
    }

    @Override
    public void incrementKeysSize() {
        size++;
    }

    @Override
    public void decrementKeysSize() {
        size--;
    }

    @Override
    public boolean isEmpty() {
        return keysSize(false) == 0;
    }

    @Override
    public boolean containsSentinel() {
        return containsSentinel;
    }

    @Override
    public void setContainsSentinel(boolean containsSentinel) {
        this.containsSentinel = containsSentinel;
    }

    @Override
    public void open() {
        pagesInMemory.add(this);
        isOpen = true;
    }

    @Override
    public void close(boolean verbose) {
        if (verbose) {
            StringJoiner pageContent = new StringJoiner(" ");
            for (Key key : keys()) {
                pageContent.add(String.valueOf(key));
            }
            StdOut.println("Page content: " + pageContent.toString());
        }
        pagesInMemory.delete(this);
        isOpen = false;
    }

    @Override
    public boolean isExternal() {
        return isExternal;
    }

    @Override
    public boolean contains(Key key) {
        return binarySearchSymbolTable.contains(key);
    }

    @Override
    public Value get(Key key) {
        if (!isExternal()) throw new IllegalArgumentException("Cannot get keys directly on an internal page");
        PageValue pageValue = binarySearchSymbolTable.get(key);
        if (pageValue == null) return null;
        return pageValue.value;
    }

    @Override
    public PageSymbolTableInterface<Key, Value> getParentPage() {
        return parentPage;
    }

    @Override
    public void setParentPage(PageSymbolTableInterface<Key, Value> parentPage) {
        this.parentPage = parentPage;
    }

    @Override
    public PageSymbolTableInterface<Key, Value> getChildPage(Key key) {
        if (isExternal()) throw new IllegalArgumentException("External pages have no children");
        if (!binarySearchSymbolTable.contains(key)) return null;
        return binarySearchSymbolTable.get(key).childPage;
    }

    @Override
    public void add(Key key, Value value) {
        if (!isExternal()) throw new IllegalArgumentException("Cannot add key directly to an internal page");
        PageValue pageValue = new PageValue(null, value);
        binarySearchSymbolTable.put(key, pageValue);
    }

    @Override
    public void addPage(PageSymbolTableInterface<Key, Value> page) {
        Key minKey = ((BinarySearchSymbolTablePage<Key, Value>) page).binarySearchSymbolTable.min();
        // If we are adding another page to this page, this means that this is an internal page
        PageValue pageValue = new PageValue(page, null);
        binarySearchSymbolTable.put(minKey, pageValue);
        page.setParentPage(this);
    }

    @Override
    public void addAllKeysFromPage(PageSymbolTableInterface<Key, Value> page) {
        BinarySearchSymbolTablePage<Key, Value> binarySearchSymbolTablePage = (BinarySearchSymbolTablePage<Key, Value>) page;
        for (Key key : page.keys()) {
            PageValue pageValue = binarySearchSymbolTablePage.binarySearchSymbolTable.get(key);
            binarySearchSymbolTable.put(key, pageValue);
            PageSymbolTableInterface<Key, Value> childPage = pageValue.childPage;
            if (childPage != null) {
                size += childPage.keysSize(true);
                childPage.setParentPage(this);
            } else {
                size++;
            }
        }
    }

    @Override
    public Key min() {
        if (isEmpty()) return null;
        Key minKey;
        if (isExternal() && containsSentinel()) {
            minKey = binarySearchSymbolTable.select(1);
        } else {
            minKey = binarySearchSymbolTable.min();
        }
        return minKey;
    }

    @Override
    public Key max() {
        if (isEmpty()) return null;
        return binarySearchSymbolTable.max();
    }

    @Override
    public Key floor(Key key) {
        return binarySearchSymbolTable.floor(key);
    }

    @Override
    public Key ceiling(Key key) {
        return binarySearchSymbolTable.ceiling(key);
    }

    @Override
    public Key select(int index) {
        if (!isExternal()) throw new IllegalArgumentException("Cannot select directly on an internal page");
        int indexToSearch = index;
        if (containsSentinel()) indexToSearch++;
        return binarySearchSymbolTable.select(indexToSearch);
    }

    @Override
    public int rank(Key key) {
        if (!isExternal()) throw new IllegalArgumentException("Cannot compute rank directly on an internal page");
        int rank = binarySearchSymbolTable.rank(key);
        if (containsSentinel()) rank--;
        return rank;
    }

    @Override
    public void delete(Key key) {
        if (!isExternal()) throw new IllegalArgumentException("Cannot delete directly on an internal page");
        // BTreeSymbolTable guarantees that the key being deleted is in the page
        size--;
        binarySearchSymbolTable.delete(key);
    }

    @Override
    public void deletePageWithKey(Key key) {
        if (isExternal()) throw new IllegalArgumentException("Delete page operation must be called on an internal page");
        if (!binarySearchSymbolTable.contains(key)) return;
        PageSymbolTableInterface<Key, Value> pageToDelete = binarySearchSymbolTable.get(key).childPage;
        size -= pageToDelete.keysSize(true);
        binarySearchSymbolTable.delete(key);
    }

    @Override
    public void deleteMin() {
        // Used as part of BTreeSymbolTable general delete method, which handles page size updates
        binarySearchSymbolTable.deleteMin();
    }

    @Override
    public void deleteMax() {
        // Used as part of BTreeSymbolTable general delete method, which handles page size updates
        binarySearchSymbolTable.deleteMax();

    }

    @Override
    public PageSymbolTableInterface<Key, Value> next(Key key) {
        if (isExternal()) throw new IllegalArgumentException("Next cannot be called on an external page");
        Key nextKey = binarySearchSymbolTable.floor(key);
        if (nextKey == null) return null;
        PageValue pageValue = binarySearchSymbolTable.get(nextKey);
        return pageValue.childPage;
    }

    @Override
    public boolean isFull() {
        return binarySearchSymbolTable.size() == MAX_NUMBER_OF_NODES;
    }

    @Override
    public PageSymbolTableInterface<Key, Value> split() {
        List<Key> keysToMove = new ArrayList<>();
        int middleRank = binarySearchSymbolTable.size() / 2;
        for (int index = middleRank; index < binarySearchSymbolTable.size(); index++) {
            Key keyToMove = binarySearchSymbolTable.select(index);
            keysToMove.add(keyToMove);
        }
        PageSymbolTableInterface<Key, Value> newPage = new BinarySearchSymbolTablePage<>(isExternal, pagesInMemory);
        int keysToMoveSize = 0;
        for (Key key : keysToMove) {
            PageValue pageValue = binarySearchSymbolTable.get(key);
            PageSymbolTableInterface<Key, Value> childPage = pageValue.childPage;
            Value value = pageValue.value;
            if (!isExternal()) {
                keysToMoveSize += childPage.keysSize(true);
                newPage.addPage(childPage);
            } else {
                keysToMoveSize++;
                newPage.add(key, value);
            }
            binarySearchSymbolTable.delete(key);
        }
        // No need to set containsSentinel because right nodes will never have it
        newPage.setKeysSize(keysToMoveSize);
        size -= keysToMoveSize;
        return newPage;
    }

    @Override
    public Iterable<Key> keys() {
        return binarySearchSymbolTable.keys();
    }

    @Override
    public int maxNumberOfNodes() {
        return MAX_NUMBER_OF_NODES;
    }
}
