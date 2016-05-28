package Evaluate.Expression;

/**
 * Created by Илнар on 19.03.2015.
 */
public class UnaryMinus extends UnaryOperation {
    public UnaryMinus (AnyExpression child) {
        super(child);
    }

    public int function(int value) {
        return -value;
    }
}
