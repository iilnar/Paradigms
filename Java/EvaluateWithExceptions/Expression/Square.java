package Evaluate.Expression;

/**
 * Created by Илнар on 24.03.2015.
 */
public class Square extends UnaryOperation {
    public Square(AnyExpression child) {
        super(child);
    }

    public int function(int value) {
        return value * value;
    }
}
