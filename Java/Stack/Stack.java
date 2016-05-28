package Stack;

/**
 * Created by ����� on 15.03.2015.
 */
public interface Stack {
    void push(Object o);
    Object pop();
    Object top();
    int size();
    boolean isEmpty();
    void clear();
    Stack makeCopy();
}
