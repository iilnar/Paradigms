package Evaluate.Expression;

/**
 * Created by ����� on 24.03.2015.
 */
public class Mod extends BinaryOperation {
    public Mod(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int function(int left, int right){
        return left % right;
    }
}
