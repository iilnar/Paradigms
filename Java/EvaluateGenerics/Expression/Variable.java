package Evaluate.Expression;

import Evaluate.Type.ONumber;

/**
 * Created by Илнар on 15.03.2015.
 */
public class Variable<T extends ONumber<T>> extends AnyOperation<T> {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public T evaluate(T x, T y, T z) {
        return name.equals("x") ? x : name.equals("y") ? y : z;
    }
}
