package Evaluate.Expression;

import Evaluate.Type.ONumber;

/**
 * Created by Илнар on 15.03.2015.
 */
public class Multiply<T extends ONumber<T>> extends BinaryOperation<T> {
    public Multiply(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public T function(T left, T right) {
        return left.multiply(right);
    }
}
