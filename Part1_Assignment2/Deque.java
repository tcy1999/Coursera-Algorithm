import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Null argument in Deque.addFirst()");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        size++;
        if (size == 1)
            last = first;
        else
            oldFirst.prev = first;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Null argument in Deque.addLast()");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        size++;
        if (size == 1)
            first = last;
        else
            oldLast.next = last;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Call removeFirst() when deque is empty");
        Item item = first.item;
        first = first.next;
        size--;
        if (size == 0)
            last = null;
        else
            first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Call removeLast() when deque is empty");
        Item item = last.item;
        last = last.prev;
        size--;
        if (size == 0)
            first = null;
        else
            last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // linked-list implementation of iterator
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() in the iterator is unsupported");
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException(
                        "Calls next() in the iterator when no more items to return");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        StdOut.println(deque.removeLast());
        deque.addLast(1);
        StdOut.println(deque.removeFirst());
        for (int i = 0; i < 10; i++)
            deque.addFirst(i);
        StdOut.println(deque.size());
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext())
            StdOut.println(iterator.next());
        for (int i = 0; i < 10; i++)
            StdOut.println(deque.removeLast());
        for (int i = 0; i < 10; i++)
            deque.addLast(i);
        for (int i = 0; i < 10; i++)
            StdOut.println(deque.removeFirst());
        StdOut.println(deque.isEmpty());
    }
}
