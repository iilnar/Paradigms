package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;

/**
 * Created by Илнар on 31.03.2015.
 */
public class Sqrt extends UnaryOperation {
    public Sqrt(AnyExpression child) {
        super(child);
    }

    public int function(int value) {
        if (value < 0) {
            throw new EvaluateException("sqrt from negative value");
        }
        int left = 0, right = 46340, m;
        while (right - left > 1) {
            m = (left + right) / 2;
            if (m * m <= value) {
                left = m;
            } else {
                right = m;
            }
        }
        return left;
    }
}
