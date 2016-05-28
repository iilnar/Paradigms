package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;
import Evaluate.Type.ONumber;

/**
 * Created by Илнар on 24.03.2015.
 */
public class Abs<T extends ONumber<T>> extends UnaryOperation<T> {
    public Abs(AnyExpression child) {
        super(child);
    }

    public T function(T value) {
        return value.abs();
    }

}
