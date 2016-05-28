package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;

/**
 * Created by Илнар on 30.03.2015.
 */
public class CheckedNegate extends UnaryOperation {
    public CheckedNegate(AnyExpression child) {
        super(child);
    }

    public int function(int value) {
        if (value == Integer.MIN_VALUE) {
            throw new EvaluateException("Overflow");
        }
        return -value;
    }
}
