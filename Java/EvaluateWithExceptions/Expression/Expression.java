package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;

/**
 * Created by ����� on 15.03.2015.
 */
public interface Expression {
    int evaluate(int x) throws EvaluateException;
}
