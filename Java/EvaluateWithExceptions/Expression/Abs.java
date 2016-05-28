package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;

/**
 * Created by Илнар on 24.03.2015.
 */
public class Abs extends UnaryOperation {
    public Abs(AnyExpression child) {
        super(child);
    }

    public int function(int value) {
        if (value == Integer.MIN_VALUE) {
            throw new EvaluateException("Overflow");
        }
        return Math.abs(value);
    }

}
