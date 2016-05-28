package Evaluate.Parser;

import Evaluate.Expression.TripleExpression;
import Evaluate.Type.ONumber;
import Evaluate.Type.TypeBuilder;

/**
 * Created by Илнар on 22.03.2015.
 */
public interface Parser<T extends ONumber<T>> {
    TripleExpression<T> parse(String expression) throws Evaluate.Exception.Exception;
}
