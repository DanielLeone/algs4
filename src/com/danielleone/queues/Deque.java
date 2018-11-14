import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.IllegalArgumentException;
import java.lang.System;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        public Node next, prev;
        public Item item;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class NodeIterator implements Iterator<Item> {
        Node node;
        public NodeIterator(Node node) {
            this.node = node;
        }

        public Item next() {
            if (node == null) {
                throw new NoSuchElementException("avocado");
            }
            Item i = node.item;
            node = node.next;
            return i;
        }


        public boolean hasNext() {
            return node.next != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("carrot");
        }
    }

    private Node first, last;
    private int count;

    /**
     * construct an empty deque
     */
    public Deque() {
        count = 0;
    }

    /**
     * is the deque empty?
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * return the number of items on the deque
     */
    public int size() {
        return count;
    }

    /**
     * add the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("avocado");
        }
        Node node = new Node(item);
        if (!isEmpty()) {
            node.next = first;
            first.prev = node;
        } else {
            last = node;
        }
        first = node;
        ++count;
    }

    /**
     * add the item to the end
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("banana");
        }
        Node node = new Node(item);
        if (!isEmpty()) {
            last.next = last;
            node.prev = last;
        } else {
            first = node;
        }
        last = node;
        ++count;
    }

    /**
     * remove and return the item from the front
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("avocado");
        }
        --count;
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        return item;
    }

    /**
     * remove and return the item from the end
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("banana");
        }
        --count;
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        return item;
    }

    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new NodeIterator(first);
    }

    /**
     * unit testing (optional)
     */
    public static void main(String[] args) {
        Deque<Integer> q = new Deque<Integer>();
        assert q.size() == 0;
        
        // try {
        //     q.removeLast();
        //     throw new Exception("expected exception");
        // } catch (NoSuchElementException error) {
        // }
        
        // try {
        //     q.removeFirst();
        //     throw new Exception("expected exception");
        // } catch (NoSuchElementException error) {
        // }

        q.addFirst(1);
        assert q.size() == 1;
        assert q.removeFirst() == 1;
        assert q.size() == 0;

        q.addFirst(1);
        q.addFirst(2);
        q.addFirst(3);
        assert q.size() == 3;
        assert q.removeFirst() == 3;
        assert q.removeFirst() == 2;
        assert q.removeFirst() == 1;
        assert q.size() == 0;

        q.addLast(1);
        q.addLast(2);
        q.addLast(3);
        assert q.size() == 3;
        assert q.removeLast() == 3;
        assert q.removeLast() == 2;
        assert q.removeLast() == 1;
        assert q.size() == 0;
        
        // try {
        //     q.removeLast();
        //     throw new Exception("expected exception");
        // } catch (NoSuchElementException error) {
        // }
        
        // try {
        //     q.removeFirst();
        //     throw new Exception("expected exception");
        // } catch (NoSuchElementException error) {
        // }
    }
}