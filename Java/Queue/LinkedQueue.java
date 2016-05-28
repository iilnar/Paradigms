import java.util.function.Predicate;

/**
 * Created by Илнар on 08.03.2015.
 */
public class LinkedQueue extends AbstractQueue implements Queue{
    private static class Node {
        Object value;
        Node prev, next;
        public Node(Node prev, Object value, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
        public Node() {
        }
    }
    private Node head, tail;

    public void enqueue(Object element) {
        size++;
        final Node old = tail;
        final Node newNode = new Node(tail, element, null);
        tail = newNode;
        if (old == null) {
            head = newNode;
        } else {
            old.next = newNode;
        }
    }
    //size = size' + 1 && tail.value = element

    //!isEmpty()
    public Object element() {
        assert size != 0;
        return head.value;
    }
    //result = head.value

	//!isEmpty()
    public Object dequeue() {
        Object res = element();
        Node next = head.next;
        head = next;
        size--;
        if (next == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        return res;
    }
    //size = size' - 1 && result  = head.value && head = head'.next 

    public LinkedQueue emptyCopy() {
    	return new LinkedQueue();
    }
}