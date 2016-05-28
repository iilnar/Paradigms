public class Test {
	public static void main(String[] args) {
		ArrayQueue queue = new ArrayQueue();
		for (int i = 0; i < 10; i++) {
			queue.enqueue(i);
		}
		for (int i = 0; i < 10; i++) {
			queue.enqueue(i);
		}
		ArrayQueue copy = queue.makeCopy();
		System.out.println("Original");
		while (!queue.isEmpty()) {
			System.out.println(queue.dequeue());
		}
		System.out.println("Copy");
		while (!copy.isEmpty()) {
			System.out.println(copy.dequeue());
		}
	}
}