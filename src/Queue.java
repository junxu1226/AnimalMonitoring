
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;

public class Queue<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int numElements;        // number of elements on queue
    private int queueSize;          // volumn
    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }
    /**
     * Initializes an empty queue.
     */
    public Queue(int queueSize) {
        this.first = null;
        this.last  = null;
        this.numElements = 0;
        this.queueSize = queueSize;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return numElements;
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }

    public Item peek_tail() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return last.item;
    }

    /**
     * Adds the item to this queue.
     *
     * @param  item the item to add
     */
    public void enqueue(Item item) {
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.previous = null;
        if (isEmpty()) first = last;
        else {
            if (numElements >= queueSize) { dequeue(); }
            oldlast.next = last;
            last.previous = oldlast;
        }
        numElements++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     * @throws NoSuchElementException if this queue is empty
     */
    public void dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        //Integer item = first.value;
        first = first.next;
        numElements--;
        if (isEmpty()) last = null;   // to avoid loitering
        else { first.previous = null; }
        //return item;
    }

    /**
     * Returns a string representation of this queue.
     *
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    public List<Item> toList() {
        List<Item> list = new ArrayList<>();
        Node<Item> current = first;
        while(current!= null) {
            list.add(current.item);
            current = current.next;
        }
        return list;
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * Unit tests the {@code Queue} data type.
     *
     * @param args the command-line arguments
     */
    // public static void main(String[] args) {
    //     Queue<String> queue = new Queue<String>();
    //     // while (!StdIn.isEmpty()) {
    //     //     String item = StdIn.readString();
    //     //     if (!item.equals("-"))
    //     //         queue.enqueue(item);
    //     //     else if (!queue.isEmpty())
    //     //         StdOut.print(queue.dequeue() + " ");
    //     // }
    //     // StdOut.println("(" + queue.size() + " left on queue)");
    // }
}
