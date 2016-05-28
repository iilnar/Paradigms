package Evaluate;

import Evaluate.Expression.*;
import Evaluate.Parser.CheckedParser;

/**
 * Created by Илнар on 15.03.2015.
 */
public class OwnTester {
    public static void main(String[] args) {
        String s = "2 - - - -- - - - -  4";
        AnyExpression expression = new CheckedParser().parse(s);
        System.out.println(expression.evaluate(2, 2, 8));
    }
}