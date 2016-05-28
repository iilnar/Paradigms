package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;

/**
 * Created by Илнар on 30.03.2015.
 */
public class CheckedDivide extends BinaryOperation {
    public CheckedDivide(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int function(int left, int right) {
        if (right == 0) {
            throw new EvaluateException("Division by zero");
        }
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new EvaluateException("Overflow");
        }
        return left / right;
    }
}
