package Evaluate.Parser;

import Evaluate.Exception.*;
import Evaluate.Exception.Exception;
import Evaluate.Expression.*;

import javax.swing.plaf.synth.SynthScrollBarUI;

/**
 * Created by Илнар on 18.03.2015.
 */
public class CheckedParser implements Parser{
    enum Lexeme {
        plus, minus, mul, div, mod, shiftLeft, shiftRight, pow, log, num, open, close, var, abs, square, sqrt, end, unaryMinus;

        public boolean isBinary() {
            return (this.ordinal() <= 8);
        }

        public boolean isFunction() {
            return (this == Lexeme.abs || this == Lexeme.square || this == Lexeme.sqrt);
        }
    }


    private Lexeme currentLexeme;
    private String s, variableName;
    private int pos, num, balance;

    private AnyExpression firstPriority() throws Exception {
        AnyExpression ret = null;
        switch (currentLexeme) {
            case num:
                ret = new Const(num);
                break;
            case minus:
                currentLexeme = Lexeme.unaryMinus;
                nextLexeme();
                if (currentLexeme == Lexeme.num) {
                    ret = new Const(num);
                } else {
                    return new CheckedNegate(firstPriority());
                }
                break;
            case open:
                nextLexeme();
                ret = fourthPriority();
                if (currentLexeme != Lexeme.close) {
                    throw new SyntaxException("Unclosed brackets");
                }
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
            case sqrt:
                nextLexeme();
                return new Sqrt(firstPriority());
            case pow:

        }
        nextLexeme();
        return ret;
    }

    private AnyExpression qqqPriority() throws Exception{
        AnyExpression ret = firstPriority();
        while (currentLexeme == Lexeme.log || currentLexeme == Lexeme.pow) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = (old == Lexeme.log) ? (new Log(ret, firstPriority())) : (new Pow(ret, firstPriority()));
        }
        return ret;
    }

    private AnyExpression secondPriority() throws Exception {
        AnyExpression ret = qqqPriority();
        while (currentLexeme == Lexeme.mul || currentLexeme == Lexeme.div || currentLexeme == Lexeme.mod) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = (old == Lexeme.mul) ? (new CheckedMultiply(ret, qqqPriority())) : ((old == Lexeme.div) ? new CheckedDivide(ret, qqqPriority()) : new Mod(ret, qqqPriority()));
        }
        return ret;
    }

    private AnyExpression thirdPriority() throws Exception {
        AnyExpression ret = secondPriority();
        while (currentLexeme == Lexeme.plus || currentLexeme == Lexeme.minus) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = (old == Lexeme.plus) ? (new CheckedAdd(ret, secondPriority())) : (new CheckedSubtract(ret, secondPriority()));
        }
        return ret;
    }

    private AnyExpression fourthPriority() throws Exception {
        AnyExpression ret = thirdPriority();
        while (currentLexeme == Lexeme.shiftLeft || currentLexeme == Lexeme.shiftRight) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = (old == Lexeme.shiftLeft) ? (new ShiftLeft(ret, thirdPriority())) : (new ShiftRight(ret, thirdPriority()));
        }
        return ret;
    }

    private void nextLexeme() throws Exception{
        Lexeme previousLexeme = currentLexeme;
        while (pos < s.length() && Character.isWhitespace(s.charAt(pos))) {
            pos++;
        }
        if (pos == s.length()) {
            return;
        }
        char c = s.charAt(pos);
        if (Character.isDigit(c)) {
            num = findNum();
            currentLexeme = Lexeme.num;
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
                case "sqrt":
                    currentLexeme = Lexeme.sqrt;
                    break;
                default:
                    if (!nextToken.equals("x") && !nextToken.equals("y") && !nextToken.equals("z")) {
                        throw new SyntaxException("undefined variable or function: " + nextToken);
                    }
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
                    if (s.charAt(pos + 1) == '*') {
                        currentLexeme = Lexeme.pow;
                        pos++;
                    } else {
                        currentLexeme = Lexeme.mul;
                    }
                    break;
                case '/':
                    if (s.charAt(pos + 1) == '/') {
                        currentLexeme = Lexeme.log;
                        pos++;
                    } else {
                        currentLexeme = Lexeme.div;
                    }
                    break;
                case '(':
                    currentLexeme = Lexeme.open;
                    balance++;
                    break;
                case ')':
                    currentLexeme = Lexeme.close;
                    balance--;
                    if (balance < 0) {
                        throw new SyntaxException("Brackets");
                    }
                    break;
                case '<':
                    currentLexeme = Lexeme.shiftLeft;
                    pos++;
                    break;
                case '>':
                    currentLexeme = Lexeme.shiftRight;
                    pos++;
                    break;
                case '.':
                    currentLexeme = Lexeme.end;
                    break;
                default:
                    throw new SyntaxException("undefined character at pos: " + pos);
            }
        }
        if (previousLexeme != null && previousLexeme.isBinary()) {
            if (!currentLexeme.isFunction() && currentLexeme != Lexeme.unaryMinus && currentLexeme != Lexeme.minus && currentLexeme != Lexeme.open && currentLexeme != Lexeme.num && currentLexeme != Lexeme.var) {
                throw new SyntaxException("Error");
            }
        }
        if (currentLexeme.isBinary() && currentLexeme != Lexeme.unaryMinus && currentLexeme != Lexeme.minus) {
            if (previousLexeme != Lexeme.num && previousLexeme != Lexeme.var && previousLexeme != Lexeme.close) {
                throw new SyntaxException("Error");
            }
        }
        pos++;
    }

    private int findNum() throws Exception {
        int begin = pos;
        while (pos < s.length() && Character.isDigit(s.charAt(pos))) {
            pos++;
        }
        int res = Integer.parseInt((currentLexeme == Lexeme.unaryMinus ? "-" : "") + s.substring(begin, pos--));
        return res;
    }

    private String findVariableName() {
        int begin = pos;
        while (pos < s.length() && Character.isAlphabetic(s.charAt(pos))) {
            pos++;
        }
        return s.substring(begin, pos--);
    }

    public AnyExpression parse(String s) throws Exception{
        this.pos = 0;
        this.balance = 0;
        this.s = s + ".";
        nextLexeme();
        AnyExpression ret = fourthPriority();
        if (balance != 0 || currentLexeme != Lexeme.end) {
            throw new SyntaxException("Error");
        }
        return ret;
    }
}
