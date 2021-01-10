import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = items[i];
        items = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException(
                    "Call RandomizedQueue.enqueue() with a null argument");
        if (size == items.length)
            resize(2 * items.length);
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException(
                    "Call dequeue() when RandomizedQueue is empty");
        int index = StdRandom.uniform(size);
        Item item = items[index];
        items[index] = items[--size];
        items[size] = null;
        if (size > 0 && size == items.length / 4)
            resize(items.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException(
                    "Call sample() when RandomizedQueue is empty");
        int index = StdRandom.uniform(size);
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // array implementation of iterator
    private class ArrayIterator implements Iterator<Item> {
        private final int[] indexs = StdRandom.permutation(size);
        private int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() in the iterator is unsupported");
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException(
                        "Calls next() in the iterator when no more items to return");
            return items[indexs[i++]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        randomizedQueue.enqueue(1);
        StdOut.println(randomizedQueue.sample());
        StdOut.println(randomizedQueue.isEmpty());
        StdOut.println(randomizedQueue.dequeue());
        for (int i = 0; i < 10; i++)
            randomizedQueue.enqueue(i);
        Iterator<Integer> iterator = randomizedQueue.iterator();
        while (iterator.hasNext())
            StdOut.println(iterator.next());
        StdOut.println(randomizedQueue.size());
        for (int i = 0; i < 10; i++)
            StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue.isEmpty());
    }
}
