public class ArrayQueueADT {
	private Object[] elements = new Object[8];
	private int head = 0, tail = 0;

	private static void ensureCapacity(ArrayQueueADT deque, int size) {
		if (size == deque.elements.length) {
			Object[] newElements = new Object[deque.elements.length * 2];
			for (deque.tail = 0; deque.tail < deque.elements.length; deque.tail++) {
				newElements[deque.tail] = deque.elements[(deque.tail + deque.head) % deque.elements.length];
			}
			deque.head = 0;
			deque.tail = deque.elements.length - 1;
			deque.elements = newElements;
		}
	}

	public static void enqueue(ArrayQueueADT deque, Object element) {
		ensureCapacity(deque, size(deque) + 1);
		deque.elements[deque.tail] = element;
		deque.tail = (deque.tail + 1) % deque.elements.length;
	}	

	public static void push(ArrayQueueADT deque, Object element) {
		ensureCapacity(deque, size(deque) + 1);
		deque.elements[deque.head = (deque.head - 1 + deque.elements.length) % deque.elements.length] = element;
	}

	public static Object element(ArrayQueueADT deque) {
		assert !isEmpty(deque);
		return deque.elements[deque.head];
	}

	public static Object peek(ArrayQueueADT deque) {
		assert !isEmpty(deque);
		return deque.elements[(deque.tail - 1 + deque.elements.length) % deque.elements.length];
	}

	public static Object dequeue(ArrayQueueADT deque) {
		Object res = element(deque);
		deque.elements[deque.head] = null;
		deque.head = (deque.head + 1) % deque.elements.length;
		return res;
	}

	public static Object remove(ArrayQueueADT deque) {
		Object res = peek(deque);
		deque.elements[deque.tail = (deque.tail - 1 + deque.elements.length) % deque.elements.length] = null;
		return res;
	}

	public static int size(ArrayQueueADT deque) {
		return (deque.head <= deque.tail) ? deque.tail - deque.head : deque.elements.length - deque.head + deque.tail;
	}


	public static boolean isEmpty(ArrayQueueADT deque) {
		return deque.head == deque.tail;
	}

	public static void clear(ArrayQueueADT deque) {
		for (int i = 0; i < deque.elements.length; i++) {
			deque.elements[i] = null;
		}
		deque.head = 0;
		deque.tail = 0;
	}

	public static Object[] toArray(ArrayQueueADT deque) {
		Object[] array = new Object[size(deque)];
		for (int i = 0; i < array.length; i++) {
			array[i] = deque.elements[(i + deque.head) % deque.elements.length];
		}
		return array;
	}


}