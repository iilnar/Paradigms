package Evaluate;

import Evaluate.Expression.AnyExpression;
import Evaluate.Parser.CheckedParser;
import Evaluate.Type.*;

import java.lang.Exception;

/**
 * Created by Илнар on 06.04.2015.
 */
public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        AnyExpression exp;
        TypeBuilder builder;
        switch (mode) {
            case "i":
                builder = new OSafeIntegerBuilder();
                break;
            case "d":
                builder = new ODoubleBuilder();
                break;
            case "u":
                builder = new OIntegerBuilder();
                break;
            case "b":
                builder = new OByteBuilder();
                break;
            case "f":
                builder = new OFloatBuilder();
                break;
            default:
                builder = new OBigIntegerBuilder();
        }
        exp = new CheckedParser<>(builder).parse(expression);
        Object res[][][] = new Object[x2 - x1 + 2][y2 - y1 + 2][z2 - z1 + 2];
        for (int i = 0; i <= x2 - x1 + 1; i++) {
            for (int j = 0; j <= y2 - y1 + 1; j++) {
                for (int k = 0; k <= z2 - z1 + 1; k++) {
                    try {
                        res[i][j][k] = exp.evaluate(builder.build(i + x1), builder.build(j + y1), builder.build(k + z1)).value();
                    } catch (Exception e) {
                        res[i][j][k] = null;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        try {
            Object[][][] res = new GenericTabulator().tabulate("i", "square -5", 0, 4, -17, 13, -5, 0);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
