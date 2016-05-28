package Evaluate.Expression;

import Evaluate.Type.*;

/**
 * Created by Илнар on 15.03.2015.
 */
public abstract class BinaryOperation<T extends ONumber<T>> extends AnyOperation<T> {
    protected final AnyExpression<T> leftOperand;
    protected final AnyExpression<T> rightOperand;

    abstract T function(T left, T right);

    public BinaryOperation(AnyExpression leftOperand, AnyExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public T evaluate(T x, T y, T z) {
        T res = function(leftOperand.evaluate(x, y, z), rightOperand.evaluate(x, y, z));
        return res;
    }
}
