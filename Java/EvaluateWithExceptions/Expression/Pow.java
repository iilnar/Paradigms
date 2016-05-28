package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;

/**
 * Created by Илнар on 31.03.2015.
 */
public class Pow extends BinaryOperation {
    public Pow(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int function(int left, int right) {
        if (left == 0 && right == 0) {
            throw new EvaluateException("Can't power null");
        }
        if (right < 0) {
            throw new EvaluateException("Negative power");
        }
        int res = 1;
        while (right != 0) {
            if ((right & 1) == 1) {
                res = new CheckedMultiply(new Const(res), new Const(left)).evaluate(0);
                if (right == 1) {
                    break;
                }
            }
            left = new CheckedMultiply(new Const(left), new Const(left)).evaluate(0);
            right >>= 1;
        }
        return res;
    }
}
