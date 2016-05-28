package Evaluate.Expression;

/**
 * Created by Илнар on 15.03.2015.
 */
public class Variable extends AnyOperation {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public int evaluate(int x, int y, int z) {
        return name.equals("x") ? x : name.equals("y") ? y : z;
    }
}
