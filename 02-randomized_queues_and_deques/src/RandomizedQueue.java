import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private Item[] iteratorItems;
        private int[] randomIndices;
        private int numItems = 0;
        private int currentItem = 0;

        public RandomizedQueueIterator(Item[] items, int n) {
            iteratorItems = items;
            numItems = n;
            randomIndices = new int[numItems];
            for (int i = 0; i < randomIndices.length; ++i) {
                randomIndices[i] = i;
            }
            StdRandom.shuffle(randomIndices);
        }

        @Override
        public boolean hasNext() {
            return currentItem < numItems;
        }

        @Override
        public Item next() {
            if (currentItem >= numItems) {
                throw new NoSuchElementException();
            }
            return iteratorItems[randomIndices[currentItem++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Item [] items;
    private int size;

    /**
     *  construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        size = 0;
    }

    /**
     * is the queue empty?
     * @return
     */
    public boolean isEmpty() {
        return size() <= 0;
    }

    /**
     * return the number of items on the queue
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * add the item
     *
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (items.length <= size) {

            itemsRealloc(items.length * 2);
        }

        items[size++] = item;

        randomSwap();
    }

    /**
     * delete and return a random item
     * @return
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (items.length >= size * 4) {
            itemsRealloc(size * 2);
        }

        randomSwap();

        --size;
        int randomIndex = size > 0 ? StdRandom.uniform(size) : 0;
        Item randomItem = items[randomIndex];
        items[randomIndex] = items[size];
        items[size] = null;

        return randomItem;
    }

    /**
     * return (but do not delete) a random item
     * @return
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return items[StdRandom.uniform(size)];
    }

    /**
     * return an independent iterator over items in random order
     * @return
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(items, size);
    }

    private void itemsRealloc(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];
        for (int i = 0; i < size; ++i) {
            newItems[i] = items[i];
        }
        items = null;
        items = newItems;
    }

    private void randomSwap() {
        if (size > 10) {
            int rnd1 = StdRandom.uniform(size);
            int rnd2 = StdRandom.uniform(size);

            Item old = items[rnd1];
            items[rnd1] = items[rnd2];
            items[rnd2] = old;
        } else if (size <= 10) {
            StdRandom.shuffle(items, 0, size-1);
        }
    }
}
