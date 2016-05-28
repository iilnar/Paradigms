public class ArrayQueueModule {
	private static Object[] elements = new Object[8];
	private static int head = 0, tail = 0;

	private static void ensureCapacity(int size) {
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

	public static void enqueue(Object element) {
		ensureCapacity(size() + 1);
		elements[tail] = element;
		tail = (tail + 1) % elements.length;
	}	

	public static void push(Object element) {
		ensureCapacity(size() + 1);
		elements[head = (head - 1 + elements.length) % elements.length] = element;
	}

	public static Object element() {
		assert !isEmpty();
		return elements[head];
	}
                            
	public static Object peek() {
		assert !isEmpty();
		return elements[(tail - 1 + elements.length) % elements.length];
	}

	public static Object dequeue() {
		Object res = element();
		elements[head] = null;
		head = (head + 1) % elements.length;
		return res;
	}

	public static Object remove() {
		Object res = peek();
		elements[tail = (tail - 1 + elements.length) % elements.length] = null;
		return res;
	}

	public static int size() {
		return (head <= tail) ? tail - head : elements.length - head + tail;
	}


	public static boolean isEmpty() {
		return head == tail;
	}

	public static void clear() {
		for (int i = 0; i < elements.length; i++) {
			elements[i] = null;
		}
		head = 0;
		tail = 0;
	}

	public static Object[] toArray() {
		Object[] array = new Object[size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = elements[(i + head) % elements.length];
		}
		return array;
	}


}