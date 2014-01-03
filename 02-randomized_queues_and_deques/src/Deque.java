import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        public Node next;
        public Node prev;
        public Item item;

        private Node(Node next, Node prev, Item item) {
            this.next = next;
            this.prev = prev;
            this.item = item;
        }
    }

    private Node last;
    private Node first;
    private int size;

    /**
     * construct an empty deque
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * is the deque empty?
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * return the number of items on the deque
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * insert the item at the front
     * @param item
     */
    public void addFirst(Item item) {
        throwIfNull(item);

        Node newFirst = new Node(null, null, item);

        if (size() == 0) {
            first = newFirst;
            last = newFirst;
        } else {
            newFirst.next = first;
            first.prev = newFirst;
            first = newFirst;
        }

        ++size;
    }

    /**
     * insert the item at the end
     * @param item
     */
    public void addLast(Item item) {
        throwIfNull(item);

        Node newLast = new Node(null, null, item);

        if (size() == 0) {
            first = newLast;
            last = newLast;
        } else {
            newLast.prev = last;
            last.next = newLast;
            last = newLast;
        }

        ++size;
    }

    /**
     * delete and return the item at the front
     * @return
     */
    public Item removeFirst() {
        throwIfEmpty();

        Item firstItem = first.item;
        first = first.next;
        // first == null means we've just removed last element from the deque
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        --size;
        return firstItem;
    }

    /**
     * delete and return the item at the end
     * @return
     */
    public Item removeLast() {
        throwIfEmpty();

        Item lastItem = last.item;
        last = last.prev;
        // last == null means we've just removed last element from the deque
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        --size;
        return lastItem;
    }

    /**
     * return an iterator over items in order from front to end
     * @return
     */
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node lastAccessed = first;

            @Override
            public boolean hasNext() {
                return lastAccessed != null;
            }

            @Override
            public Item next() {
                if (lastAccessed == null) {
                    throw new NoSuchElementException();
                }

                Item nextItem = lastAccessed.item;
                lastAccessed = lastAccessed.next;

                return nextItem;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void throwIfNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    private void throwIfEmpty() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
    }

}
