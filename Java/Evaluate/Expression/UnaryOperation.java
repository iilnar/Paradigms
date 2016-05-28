package Evaluate.Expression;

/**
 * Created by Илнар on 24.03.2015.
 */
public abstract class UnaryOperation extends AnyOperation{
    protected final AnyExpression child;

    abstract int function(int value);

    public UnaryOperation(AnyExpression child) {
        this.child = child;
    }

    public int evaluate(int x, int y, int z) {
        return function(child.evaluate(x, y, z));
    }
}
