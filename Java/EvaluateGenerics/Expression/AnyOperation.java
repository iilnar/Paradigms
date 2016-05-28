package Evaluate.Expression;


import Evaluate.Type.ONumber;

/**
 * Created by Илнар on 17.03.2015.
 */
abstract public class AnyOperation<T extends ONumber<T>> implements AnyExpression<T> {
    public T evaluate(T x) {
        return evaluate(x, null, null);
    }
}
