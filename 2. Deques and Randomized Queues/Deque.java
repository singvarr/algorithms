import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node firstNode;
    private Node lastNode;
    private int size;

    public Deque() {
        size = 0;
    }

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    private void validateAdd(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null to deque");
        }
    }

    private void validateRemove() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot remove item from empty deque");
        }
    }

    private void resetDeque() {
        firstNode = null;
        lastNode = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        validateAdd(item);

        Node oldFirst = firstNode;

        firstNode = new Node();

        if (oldFirst != null) {
            oldFirst.previous = firstNode;
            firstNode.next = oldFirst;
        }
        else {
            firstNode.next = null;
        }

        firstNode.item = item;
        firstNode.previous = null;

        if (lastNode == null) {
            lastNode = firstNode;
            lastNode.next = null;
        }

        size += 1;
    }

    public void addLast(Item item) {
        validateAdd(item);

        Node oldLast = lastNode;

        lastNode = new Node();
        lastNode.item = item;
        lastNode.next = null;
        lastNode.previous = oldLast;

        if (oldLast != null) {
            oldLast.next = lastNode;
        }
        else {
            firstNode = lastNode;
        }

        size += 1;
    }

    public Item removeFirst() {
        validateRemove();
        size -= 1;

        Item item = firstNode.item;

        if (size > 0) {
            firstNode = firstNode.next;
            firstNode.previous = null;
        }
        else resetDeque();

        return item;
    }

    public Item removeLast() {
        validateRemove();
        size -= 1;

        Item item = lastNode.item;

        if (size > 0) {
            lastNode = lastNode.previous;
            lastNode.next = null;
        }
        else resetDeque();

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = firstNode;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("unsupported");
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("there isn't next item");
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
