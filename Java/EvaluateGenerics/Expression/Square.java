package Evaluate.Expression;

import Evaluate.Type.ONumber;

/**
 * Created by Илнар on 07.04.2015.
 */
public class Square<T extends ONumber<T>> extends UnaryOperation<T> {
    public Square(AnyExpression child) {
        super(child);
    }

    @Override
    T function(T value) {
        return value.square();
    }
}
