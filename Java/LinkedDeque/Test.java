public class Test {
	public static void main(String[] args) {
		LinkedDeque deque = new LinkedDeque();
		for (int i = 0; i < 10; i++) {
			deque.addFirst(i);
		}
		for (int i = 0; i < 10; i++) {
			deque.addLast(i);
		}
		while (!deque.isEmpty()) {
			System.out.println(deque.removeFirst());
		}
	}
}