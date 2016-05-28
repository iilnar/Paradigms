public class ArrayQueueModule {
	private static int size = 8, head = 0, tail = 0;
	private static Object[] elements = new Object[8];

	private static void ensureCapacity() {
		if ((tail + 1) % size == head) {
			Object[] newElements = new Object[size * 2];
			for (tail = 0; tail < size; tail++) {
				newElements[tail] = elements[(tail + head) % size];
			}
			tail = size - 1;
			head = 0;
			size *= 2;
			elements = newElements;
		}
	}

	public static void enqueue(Object element) {
		assert element != null;
		ensureCapacity();
		elements[tail] = element;
		tail = (tail + 1) % size;
		
	}                             

	public static Object element() {
		assert !isEmpty();
		return elements[head];
	}

	public static Object peek() {
		assert !isEmpty();
		return elements[head];
	}

	public static Object dequeue() {
		assert !isEmpty();
		Object res = elements[head];
		elements[head] = null;
		head = (head + 1) % size;
		return res;
	}

	public static int size() {
		return (head <= tail) ? tail - head : size - head + tail;
	}
	
	public static boolean isEmpty() {
		return head == tail;
	}

	public static void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		head = 0;
		tail = 0;
	}

	public static Object[] toArray() {
		Object[] array = new Object[size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = elements[(i + head) % size];
		}
		return array;
	}

/*	public static String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; (i + head) % elements.length < tail; i++) {
			sb.append(" " + elements[(i + head) % elements.length]);
		}
		return sb.toString();
	}
*/

}