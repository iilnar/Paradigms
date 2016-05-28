package Evaluate.Expression;

/**
 * Created by Илнар on 15.03.2015.
 */
public class Multiply extends BinaryOperation {
    public Multiply(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int function(int left, int right) {
        return left * right;
    }
}
