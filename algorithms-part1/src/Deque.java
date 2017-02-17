import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

    private class Node<Item> {
        private Node<Item> prev;
        private Node<Item> next;
        private Item val;

        Node(Item obj) {
            this.val = obj;
            this.prev = null;
            this.next = null;
        }

        public Node<Item> getPrev() {
            return prev;
        }

        public Node<Item> getNext() {
            return next;
        }

        public Item getVal() {
            return val;
        }

        public void setPrev(Node<Item> p) {
            prev = p;
        }

        public void setNext(Node<Item> n) {
            next = n;
        }

    }

    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    public Deque() {
        this.head = new Node<Item>(null);
        this.tail = new Node<Item>(null);

        this.head.setNext(this.tail);
        this.tail.setPrev(this.head);

        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return size;
    }


    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Add first item is null");
        }

        insert(new Node<Item>(item), this.head.getNext());
        size++;
    }

    public void addLast(Item item)  {
        if (item == null) {
            throw new NullPointerException("Add last item is null");
        }

        insert(new Node<Item>(item), this.tail);
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }
        Node removed = remove(this.head.getNext());
        size--;

        return (Item) removed.getVal();
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }
        Node removed = remove(this.tail.getPrev());
        size--;
        return (Item) removed.getVal();
    }

    /**
     * To insert a node before the given node.
     * @param insert the node to insert
     * @param nodeAfter the node which is after the new inserted node
     */
    private void insert(Node insert, Node nodeAfter) {
        Node before = nodeAfter.getPrev();

        insert.setNext(nodeAfter);
        nodeAfter.setPrev(insert);

        before.setNext(insert);
        insert.setPrev(before);
    }

    /**
     * Remove a node from the list.
     * @param node the node to remove.
     */
    private Node remove(Node node) {
        Node before = node.getPrev();
        Node after = node.getNext();

        before.setNext(after);
        after.setPrev(before);

        node.setNext(null);
        node.setPrev(null);

        return node;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> iter;

        public DequeIterator() {
            iter = head.getNext();
        }

        @Override
        public boolean hasNext() {
            return iter.getVal() != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterate to the end.");
            }
            Item val = iter.getVal();
            iter = iter.getNext();
            return val;
        }

    }

    /**
     * Return an iterator over items in order from front to end.
     * @return an iterator over items.
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.println("is empty? " + deque.isEmpty());
        deque.addFirst("what");
        deque.addLast("after what");
        deque.addFirst("before what");
        deque.addLast("should be the end");

        System.out.println("is empty? " + deque.isEmpty());
        Iterator<String> it = deque.iterator();
        System.out.println(deque.size());
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println("-----------------");
        while (deque.size() > 0)
            System.out.println("Remove " + deque.removeLast());

        // try to add null
        try {
            deque.addFirst(null);
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

}
