package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;

/**
 * Created by Илнар on 30.03.2015.
 */
public class CheckedSubtract extends BinaryOperation {
    public CheckedSubtract(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int function(int left, int right) {
        if ((right > 0) ? (left < Integer.MIN_VALUE + right) : (left > Integer.MAX_VALUE + right)) {
            throw new EvaluateException("Overflow");
        }
        return left - right;
    }
}
