package Evaluate.Expression;

/**
 * Created by Илнар on 24.03.2015.
 */
public class ShiftRight extends BinaryOperation {
    public ShiftRight(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int function(int left, int right) {
        return (left >> right);
    }
}
