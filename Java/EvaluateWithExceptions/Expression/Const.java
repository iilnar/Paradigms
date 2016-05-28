package Evaluate.Expression;

/**
 * Created by Илнар on 15.03.2015.
 */
public class Const extends AnyOperation {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    public int evaluate(int x, int y, int z) {
        return value;
    }
}
