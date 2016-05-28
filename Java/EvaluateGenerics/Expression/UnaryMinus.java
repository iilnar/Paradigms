package Evaluate.Expression;

import Evaluate.Type.ONumber;

/**
 * Created by Илнар on 19.03.2015.
 */
public class UnaryMinus<T extends ONumber<T>> extends UnaryOperation<T> {
    public UnaryMinus (AnyExpression child) {
        super(child);
    }

    public T function(T value) {
        return value.negate();
    }
}
