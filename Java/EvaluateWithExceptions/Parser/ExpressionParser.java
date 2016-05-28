package Evaluate.Parser;

import Evaluate.Expression.*;

/**
 * Created by Илнар on 18.03.2015.
 */
public class ExpressionParser implements Parser{
    enum Lexeme {
        plus, minus, mul, div, mod, shiftLeft, shiftRight, num, open, close, var, abs, square
    }


    private Lexeme currentLexeme;
    private String s, variableName;
    private int pos, num;

    private AnyExpression firstPriority() {
        AnyExpression ret = null;
        switch (currentLexeme) {
            case num:
                ret = new Const(num);
                break;
            case minus:
                nextLexeme();
                if (currentLexeme == Lexeme.num) {
                    ret = new Const(-num);
                } else {
                    return new UnaryMinus(firstPriority());
                }
                break;
            case open:
                nextLexeme();
                ret = fourthPriority();
                break;
            case var:
                ret = new Variable(variableName);
                break;
            case abs:
                nextLexeme();
                return new Abs(firstPriority());
            case square:
                nextLexeme();
                return new Square(firstPriority());
        }
        nextLexeme();
        return ret;
    }

    private AnyExpression secondPriority() {
        AnyExpression ret = firstPriority();
        while (currentLexeme == Lexeme.mul || currentLexeme == Lexeme.div || currentLexeme == Lexeme.mod) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = (old == Lexeme.mul) ? (new Multiply(ret, firstPriority())) : ((old == Lexeme.div) ? new Divide(ret, firstPriority()) : new Mod(ret, firstPriority()));
        }
        return ret;
    }

    private AnyExpression thirdPriority() {
        AnyExpression ret = secondPriority();
        while (currentLexeme == Lexeme.plus || currentLexeme == Lexeme.minus) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = (old == Lexeme.plus) ? (new Add(ret, secondPriority())) : (new Subtract(ret, secondPriority()));
        }
        return ret;
    }

    private AnyExpression fourthPriority() {
        AnyExpression ret = thirdPriority();
        while (currentLexeme == Lexeme.shiftLeft || currentLexeme == Lexeme.shiftRight) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = (old == Lexeme.shiftLeft) ? (new ShiftLeft(ret, thirdPriority())) : (new ShiftRight(ret, thirdPriority()));
        }
        return ret;
    }

    private void nextLexeme() {
        Lexeme previousLexeme = currentLexeme;
        while (pos < s.length() && Character.isWhitespace(s.charAt(pos))) {
            pos++;
        }
        if (pos == s.length()) {
            return;
        }
        char c = s.charAt(pos);
        if (Character.isDigit(c)) {
            currentLexeme = Lexeme.num;
            num = findNum();
        } else if (Character.isAlphabetic(c)) {
            String nextToken = findVariableName();
            switch (nextToken) {
                case "abs":
                    currentLexeme = Lexeme.abs;
                    break;
                case "mod":
                    currentLexeme = Lexeme.mod;
                    break;
                case "square":
                    currentLexeme = Lexeme.square;
                    break;
                default:
                    currentLexeme = Lexeme.var;
                    variableName = nextToken;
                    break;
            }
        } else {
            switch (c) {
                case '+':
                    currentLexeme = Lexeme.plus;
                    break;
                case '-':
                    currentLexeme = Lexeme.minus;
                    break;
                case '*':
                    currentLexeme = Lexeme.mul;
                    break;
                case '/':
                    currentLexeme = Lexeme.div;
                    break;
                case '(':
                    currentLexeme = Lexeme.open;
                    break;
                case ')':
                    currentLexeme = Lexeme.close;
                    break;
                case '<':
                    currentLexeme = Lexeme.shiftLeft;
                    pos++;
                    break;
                case '>':
                    currentLexeme = Lexeme.shiftRight;
                    pos++;
                    break;
            }
        }
        pos++;
    }

    private int findNum() {
        int begin = pos;
        while (pos < s.length() && Character.isDigit(s.charAt(pos))) {
            pos++;
        }
        long res = Long.parseLong(s.substring(begin, pos--));
        return (int)res;
    }

    private String findVariableName() {
        int begin = pos;
        while (pos < s.length() && Character.isAlphabetic(s.charAt(pos))) {
            pos++;
        }
        return s.substring(begin, pos--);
    }

    public AnyExpression parse(String s) {
        this.s = s;
        nextLexeme();
        return fourthPriority();
    }
}
