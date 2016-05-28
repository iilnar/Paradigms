class ArrayDequeTester {
	public static void main(String[] args) {
		ArrayDequeSingleton.addLast(1);
		while (!ArrayDequeSingleton.isEmpty()) {
			System.out.println(ArrayDequeSingleton.removeFirst());
		}                                                         
	}
}