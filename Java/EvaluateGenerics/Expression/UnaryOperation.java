package Evaluate.Expression;

import Evaluate.Type.ONumber;

/**
 * Created by Илнар on 24.03.2015.
 */
public abstract class UnaryOperation<T extends ONumber<T>> extends AnyOperation<T>{
    protected final AnyExpression<T> child;

    abstract T function(T value);

    public UnaryOperation(AnyExpression child) {
        this.child = child;
    }

    public T evaluate(T x, T y, T z){
        T res = function(child.evaluate(x, y, z));
        return res;
    }
}
