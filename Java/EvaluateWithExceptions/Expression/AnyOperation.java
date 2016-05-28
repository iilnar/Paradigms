package Evaluate.Expression;

/**
 * Created by Илнар on 17.03.2015.
 */
abstract public class AnyOperation implements AnyExpression {
    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }
}
