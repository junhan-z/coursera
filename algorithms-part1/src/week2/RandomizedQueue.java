package week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    public RandomizedQueue() {
        size = 0;
        queue = (Item[]) new Object[5];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }

        queue = copy;
    }

    public void enqueue(Item item) {
        if  (item == null) {
            throw new NullPointerException("Enqueue a null item");
        }

        if (size == queue.length) {
            resize(2 * size);
        }

        // front + size is the end of the queue
        queue[size] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }

        int rand = StdRandom.uniform(size);
        Item val = queue[rand % size];
        queue[rand % size] = queue[size-1];
        queue[size-1] = null;

        if (size > 0 && size == queue.length/4) {
            resize(queue.length/2);
        }

        size--;
        return val;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Nothing to sample");
        }
        int rand = StdRandom.uniform(size);
        return queue[rand];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int iter;

        public RandomizedQueueIterator() {
            iter = 0;
        }

        @Override
        public boolean hasNext() {
            return iter < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterate to the end.");
            }
            
            Item val = queue[iter % size];
            iter++;
            return val;
        }

    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        Iterator<String> it = rq.iterator();
        Iterator<String> it2 = rq.iterator();
        for (int i = 0; i < 20; i++) {
            rq.enqueue("Item " + i);
            System.out.println("Enque: Item " + i + " size: " + rq.size());
        }

        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it2.next());

        while (!rq.isEmpty()) {
            System.out.println("week2.Deque: " + rq.dequeue() + " size: " + rq.size());
        }
    }

}

