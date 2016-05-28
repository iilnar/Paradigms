import java.util.function.Predicate;
import java.util.function.Function;

/**
 * Created by Илнар on 08.03.2015.
 */
public interface Queue {
    void enqueue(Object o);
    Object dequeue();
    Object element();
    int size();
    boolean isEmpty();
    void clear();
    Queue emptyCopy();
    Queue filter(Predicate t);
    Queue map(Function t);
}
