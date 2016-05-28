public interface Deque {
	void addFirst(Object o);
	void addLast(Object o);
	Object removeFirst();
	Object removeLast();
	Object getFirst();
	Object getLast();
	int size();
	boolean isEmpty();
	void clear(); 

}
