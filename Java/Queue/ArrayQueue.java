/**
 * Created by Илнар on 10.03.2015.
 */
public class ArrayQueue extends AbstractQueue implements Queue {
    //inv : elements[head] - first element of deque, elements[tail - 1] - last elements of deque
    private Object[] elements = new Object[8];
    private int head = 0, tail = 0;

    private void ensureCapacity(int size) {
        if (size == elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            for (tail = 0; tail < elements.length; tail++) {
                newElements[tail] = elements[(tail + head) % elements.length];
            }
            head = 0;
            tail = elements.length - 1;
            elements = newElements;
        }

    }
	
    public void enqueue(Object element) {
        ensureCapacity(size() + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        size++;
    }
    //all old elements saved, element is last in deque
    //post: tail = tail' + 1 && size == size' + 1 && elements[tail'] == element

    public void push(Object element) {
        ensureCapacity(size() + 1);
        elements[head = (head - 1 + elements.length) % elements.length] = element;
        size++;
    }
    //all old elements saved, element is first in deque
    //post: head = head' - 1 && size = size' + 1 && elements[head] = element

    //!isEmpty
    public Object element() {
        assert !isEmpty();
        return elements[head];
    }
    //returns first element of deque
    //post: result == elements[head]

    //!isEmpty
    public Object peek() {
        assert !isEmpty();
        return elements[(tail - 1 + elements.length) % elements.length];
    }
    //returns last elements of deque
    //post: result == elements[tail - 1];


    //!isEmpty
    public Object dequeue() {
        Object res = element();
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return res;
    }
    //returns first element of deque and removes it from deque
    // post: head = head' + 1 && size == size' - 1 && result = elements[head']

    //!isEmpty
    public Object remove() {
        Object res = peek();
        elements[tail = (tail - 1 + elements.length) % elements.length] = null;
        size--;
        return res;
    }
    //returns last element of deque and removes it from deque
    //post: tail = tail' - 1 && size == size' - 1 && result = elements[tail]

    public ArrayQueue emptyCopy() {
    	return new ArrayQueue();
    }
}
