package Evaluate.Expression;

import Evaluate.Exception.EvaluateException;
import Evaluate.Type.*;

/**
 * Created by Илнар on 17.03.2015.
 */
public interface TripleExpression<T extends ONumber<T>> {
    T evaluate(T x, T y, T z) throws EvaluateException;
}
