import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    private void validateQueueSize() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("cannot call method on empty queue");
        }
    }

    private void copyArray(int newLen) {
        Item[] newArray = (Item[]) new Object[newLen];

        for (int i = 0; i < size; i++) {
            newArray[i] = items[i];
        }

        items = newArray;
    }

    private void growArray() {
        copyArray(items.length * 2);
    }

    private void shrinkArray() {
        copyArray(items.length / 2);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("put correct item in queue");
        }

        if (size == items.length) growArray();

        items[size] = item;
        size += 1;
    }

    public Item dequeue() {
        validateQueueSize();

        int index = StdRandom.uniform(size);
        int lastItemIndex = size - 1;

        Item item = items[index];
        items[index] = items[lastItemIndex];
        items[lastItemIndex] = null;
        size -= 1;

        if (size > 0 && size == items.length / 4) shrinkArray();

        return item;
    }

    public Item sample() {
        validateQueueSize();

        int index = StdRandom.uniform(size);
        return items[index];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] iterItems;
        private int currentElem;

        public RandomizedQueueIterator() {
            iterItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                iterItems[i] = items[i];
            }
            StdRandom.shuffle(iterItems);
        }

        public boolean hasNext() {
            return currentElem < iterItems.length;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("unsupported");
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("hasn't next item");
            }

            Item elem = iterItems[currentElem];
            currentElem++;
            return elem;
        }
    }
}
