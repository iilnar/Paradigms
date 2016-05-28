public class ArrayQueueADT {
	private int size = 8, head = 0, tail = 0;
	private Object[] elements = new Object[8];

	private static void ensureCapacity(ArrayQueueADT queue) {
		if ((queue.tail + 1) % queue.size == queue.head) {
			Object[] newElements = new Object[queue.size * 2];
			for (queue.tail = 0; queue.tail < queue.size; queue.tail++) {
				newElements[queue.tail] = queue.elements[(queue.tail + queue.head) % queue.size];
			}
			queue.tail = queue.size - 1;
			queue.head = 0;
			queue.size *= 2;
			queue.elements = newElements;
		}
	}

	public static void enqueue(ArrayQueueADT queue, Object element) {
		assert element != null;
		ensureCapacity(queue);
		queue.elements[queue.tail] = element;
		queue.tail = (queue.tail + 1) % queue.size;
		
	}                             

	public static Object element(ArrayQueueADT queue) {
		assert !isEmpty(queue);
		return queue.elements[queue.head];
	}

	public static Object peek(ArrayQueueADT queue) {
		assert !isEmpty(queue);
		return queue.elements[queue.head];
	}

	public static Object dequeue(ArrayQueueADT queue) {
		assert !isEmpty(queue);
		Object res = queue.elements[queue.head];
		queue.elements[queue.head] = null;
		queue.head = (queue.head + 1) % queue.size;
		return res;
	}

	public static int size(ArrayQueueADT queue) {
		return (queue.head <= queue.tail) ? queue.tail - queue.head : queue.size - queue.head + queue.tail;
	}
	
	public static boolean isEmpty(ArrayQueueADT queue) {
		return queue.head == queue.tail;
	}

	public static void clear(ArrayQueueADT queue) {
		for (int i = 0; i < queue.size; i++) {
			queue.elements[i] = null;
		}
		queue.head = 0;
		queue.tail = 0;
	}

	public static Object[] toArray(ArrayQueueADT queue) {
		Object[] array = new Object[size(queue)];
		for (int i = 0; i < array.length; i++) {
			array[i] = queue.elements[(i + queue.head) % queue.elements.length];
		}
		return array;
	}


}