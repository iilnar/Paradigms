package Evaluate.Parser;

import Evaluate.Expression.TripleExpression;

/**
 * Created by ����� on 22.03.2015.
 */
public interface Parser {
    TripleExpression parse(String expression) throws Evaluate.Exception.Exception;
}
