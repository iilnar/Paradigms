package Stack;

/**
 * Created by Илнар on 15.03.2015.
 */
public class ArrayStack implements Stack{
    private Object[] elements = new Object[8];
    private int size;

    void ensureCapacity() {
        if (size == elements.length) {
            Object[] newElements = new Object[size  >> 1];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    public void push(Object o) {
        ensureCapacity();
        elements[size++] = o;
    }

    public Object top() {
        assert size != 0;
        return elements[size - 1];
    }

    public Object pop() {
        assert size != 0;
        Object ret = top();
        elements[--size] = null;
        return ret;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        elements = new Object[8];
        size = 0;
    }

    public ArrayStack makeCopy() {
        ArrayStack copy = new ArrayStack();
        copy.size = size;
        copy.elements = new Object[size];
        for (int i = 0; i < size; i++) {
            copy.elements[i] = elements[i];
        }
        return copy;
    }

}
