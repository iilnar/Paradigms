package Evaluate.Expression;

/**
 * Created by Илнар on 15.03.2015.
 */
public class Divide extends BinaryOperation {
    public Divide(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int function(int left, int right) {
        return left / right;
    }
}
