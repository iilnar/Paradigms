package Evaluate.Expression;

/**
 * Created by Илнар on 15.03.2015.
 */
public abstract class BinaryOperation extends AnyOperation {
    protected final AnyExpression leftOperand;
    protected final AnyExpression rightOperand;

    abstract int function(int left, int right);

    public BinaryOperation(AnyExpression leftOperand, AnyExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public int evaluate(int x, int y, int z) {
        int res = function(leftOperand.evaluate(x, y, z), rightOperand.evaluate(x, y, z));
        return res;
    }
}
