package Evaluate.Expression;

import Evaluate.Exception.*;

/**
 * Created by Илнар on 30.03.2015.
 */
public class CheckedAdd extends BinaryOperation {
    public CheckedAdd(AnyExpression leftOpenand, AnyExpression rightOperand) {
        super(leftOpenand, rightOperand);
    }

    public int function(int left, int right) {
        if (right > 0 ? left > Integer.MAX_VALUE - right : left < Integer.MIN_VALUE - right) {
            throw new EvaluateException("Overflow");
        }
        return left + right;
    }
}
