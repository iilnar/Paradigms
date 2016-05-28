package Evaluate;

import Evaluate.Expression.AnyExpression;
import Evaluate.Parser.CheckedParser;
import Evaluate.Type.OBigInteger;
import Evaluate.Type.OBigIntegerBuilder;
import Evaluate.Type.TypeBuilder;

/**
 * Created by Илнар on 15.03.2015.
 */
public class OwnTester {
    public static void main(String[] args) {
        String expression = "(-1) mod (-2)";
        TypeBuilder  builder = new OBigIntegerBuilder();
        AnyExpression exp = new CheckedParser<>(builder).parse(expression);
        System.out.println(exp.evaluate(new OBigInteger(2), new OBigInteger(2), new OBigInteger(2)));
    }
}