import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static int INITIAL_SIZE = 2;

    private Item[] queue;
    private int size = 0;

    public RandomizedQueue() {
        // cast needed since no generic array creation in Java
        queue = (Item[]) new Object[INITIAL_SIZE];
    }
    
    /**
     * is the deque empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * the number of items on the queue.
     */
    public int size() {
        return size;
    }
    
    /**
     * add an item to the queue
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        
        if (size == queue.length) {
            resize(2 * queue.length);
        }
        
        queue[size++] = item;
    }
    
    /**
     * resize the array to a new capacity, copying accross the old array
     */
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        
        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }
        
        queue = temp;
    }
    
    /**
     * delete and return a random item
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(size);
        Item item = queue[randomIndex];
        
        // move the last item to fill the gap
        if (randomIndex != size - 1) {
            queue[randomIndex] = queue[size - 1];
        }
        // set the last item to null
        queue[size - 1] = null;
        size--;
        
        if (size < queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }
    
    /**
     *  return (but do not delete) a random item
     */
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(size);
        return queue[randomIndex];
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int index = 0;
        private int[] random;
        
        public ArrayIterator() {
            random = new int[size];
            for (int i = 0; i < random.length; i++) {
                random[i] = i;
            }
            StdRandom.shuffle(random);
        }
        
        public boolean hasNext() {
            return index < random.length;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int randomIndex = random[index];
            index++;
            return queue[randomIndex];
        }
    }
    
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
}