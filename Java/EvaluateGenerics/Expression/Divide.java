package Evaluate.Expression;

import Evaluate.Type.ONumber;

/**
 * Created by Илнар on 15.03.2015.
 */
public class Divide<T extends ONumber<T>> extends BinaryOperation<T> {
    public Divide(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public T function(T left, T right) {
        return left.divide(right);
    }
}
