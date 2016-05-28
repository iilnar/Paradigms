package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;

/**
 * Created by Илнар on 17.03.2015.
 */
public interface TripleExpression {
    int evaluate(int x, int y, int z) throws EvaluateException;
}
