public class ArrayQueue {
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
	}	
	//all old elements saved, element is last in deque
	//post: tail = tail' + 1 && size == size' + 1 && elements[tail'] == element

	public void push(Object element) {
		ensureCapacity(size() + 1);
		elements[head = (head - 1 + elements.length) % elements.length] = element;
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
		return res;
	}
	//returns first element of deque and removes it from deque
	// post: head = head' + 1 && size == size' - 1 && result = elements[head']

	//!isEmpty
	public Object remove() {
		Object res = peek();
		elements[tail = (tail - 1 + elements.length) % elements.length] = null;
		return res;
	}
	//returns last element of deque and removes it from deque
	//post: tail = tail' - 1 && size == size' - 1 && result = elements[tail]

	public int size() {
		return (head <= tail) ? tail - head : elements.length - head + tail;
	}
	//return the count of elements in deque


	public boolean isEmpty() {
		return head == tail;
	}
	//Check is deque empty or not

	public void clear() {
		for (int i = 0; i < elements.length; i++) {
			elements[i] = null;
		}
		head = 0;
		tail = 0;
	}
	//clear all deque

	public Object[] toArray() {
		Object[] array = new Object[size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = elements[(i + head) % elements.length];
		}
		return array;
	}
	//represents deque as array from first to last element of deque 
	//post: return == Object[size] : Object[i] == i-th element of deque

}