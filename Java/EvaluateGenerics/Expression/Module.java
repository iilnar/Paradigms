package Evaluate.Expression;

import Evaluate.Type.ONumber;

/**
 * Created by Илнар on 07.04.2015.
 */
public class Module<T extends ONumber<T>> extends BinaryOperation<T> {

    public Module (AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    T function(T left, T right) {
        return left.module(right);
    }
}
