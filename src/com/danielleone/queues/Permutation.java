import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;

public class Permutation 
{
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> deque = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            deque.enqueue(StdIn.readString());       
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(deque.dequeue());
        }
    }
}
