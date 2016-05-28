package Evaluate;

import Evaluate.Expression.*;

/**
 * Created by Илнар on 15.03.2015.
 */
public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        AnyExpression expression = parser.parse("(((((z)-((-1291012719)+((z) mod (x))))+( square (y)))-((z)>>(1811403694)))>>( square (y)))-(((((-1709665912)*(z))+((z)+(x)))>>(-1928817253))+(((946145172)>>(z))+((665719169)+(x))))");
        System.out.println(expression.evaluate(-303657602, 829055321, -263214712));
        //: Expected -365753147, found 564927010

    }
}