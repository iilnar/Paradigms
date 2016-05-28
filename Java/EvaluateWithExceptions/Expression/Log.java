package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;

/**
 * Created by Илнар on 31.03.2015.
 */
public class Log extends BinaryOperation {
    public Log(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    int function(int left, int right) {
        if (right <= 1 || left <= 0) {
            throw new EvaluateException("Bad log");
        }
        int l = -1, r = 31;
        while (r - l > 1) {
            int m = (l + r) / 2;
            try {
                int res = new Pow(new Const(right), new Const(m)).evaluate(0);
                if (res <= left) {
                    l = m;
                } else {
                    r = m;
                }
            } catch (EvaluateException e) {
                r = m;
            }
        }
        return l;
    }
}
