public class LinkedDeque implements Deque{
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
	private int size;

	public void addFirst(Object element) {
		size++;
		final Node old = head;
		final Node newNode = new Node(null, element, head);
		head = newNode;
		if (old == null) {
			tail = newNode;
		} else {
			old.prev = newNode;
		}
	}	

	public void addLast(Object element) {
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

	public Object getFirst() {
		assert size != 0;
		return head.value;
	}

	public Object getLast() {
		assert size != 0;
		return tail.value;
	}

	public Object removeFirst() {
		Object res = getFirst();
		Node next = head.next;
		head.value = null;
		head.next = null;
		head = next;
		size--;
		if (next == null) {
			tail = null;			
		} else {
			head.prev = null;
		}
		return res;
	} 

	public Object removeLast() {
		Object res = getLast();
		Node prev = tail.prev;
		tail.value = null;
		tail.prev = null;
		tail = prev;
		size--;
		if (prev == null) {
			head = null;
		} else {
			tail.next = null;
		}
		return res;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

}