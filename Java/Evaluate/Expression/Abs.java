package Evaluate.Expression;

/**
 * Created by ����� on 24.03.2015.
 */
public class Abs extends UnaryOperation {
    public Abs(AnyExpression child) {
        super(child);
    }

    public int function(int value) {
        return Math.abs(value);
    }

}
