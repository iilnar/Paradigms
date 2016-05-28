import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Илнар on 10.03.2015.
 */

public abstract class AbstractQueue implements Queue{
    protected int size = 0;

    public abstract Object dequeue();
    public abstract void enqueue(Object o);
    public abstract Object element();
    public abstract Queue emptyCopy();
        
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

	public void clear() {
		while (!isEmpty()) {
			dequeue();
		}
	}

    public Queue filter(Predicate t) {
        Queue newQueue = emptyCopy();
		for (int i = 0; i < size; i++) {
            Object value = dequeue();
            if (t.test(value)) {
                newQueue.enqueue(value);
            }
            enqueue(value);
        }
        return newQueue;
    }

    public Queue map(Function t) {
        Queue newQueue = emptyCopy();
		for (int i = 0; i < size; i++) {
			Object value = dequeue();
            newQueue.enqueue(t.apply(value));
            enqueue(value);
        }
        return newQueue;
    }

}