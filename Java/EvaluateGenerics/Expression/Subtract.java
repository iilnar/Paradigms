package Evaluate.Expression;

import Evaluate.Type.ONumber;

/**
 * Created by Илнар on 15.03.2015.
 */
public class Subtract<T extends ONumber<T>> extends BinaryOperation<T> {
    public Subtract(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public T function(T left, T right) {
        return left.subtract(right);
    }
}
