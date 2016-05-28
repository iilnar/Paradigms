package Stack;

/**
 * Created by Илнар on 15.03.2015.
 */
public class LinkedStack implements Stack {
    private class Node {
        Node prev;
        Object value;

        public Node(Node prev, Object value) {
            this.prev = prev;
            this.value = value;
        }

        public Node() {}
    }
    private int size;
    private Node head;

    public void push(Object o) {
        head = new Node(head, o);
        size++;
    }

    public Object top() {
        assert size != 0;
        return head.value;
    }

    public Object pop() {
        Object ret = top();
        head = head.prev;
        size--;
        return ret;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public LinkedStack makeCopy() {
        LinkedStack copy = new LinkedStack();
        copy.head = head;
        copy.size = size;
        return copy;
    }

}
