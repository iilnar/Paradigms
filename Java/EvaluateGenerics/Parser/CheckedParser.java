package Evaluate.Parser;

import Evaluate.Exception.Exception;
import Evaluate.Exception.SyntaxException;
import Evaluate.Expression.*;
import Evaluate.Type.ONumber;
import Evaluate.Type.TypeBuilder;

/**
 * Created by Илнар on 18.03.2015.
 */
public class CheckedParser<T extends ONumber<T>> implements Parser{

    enum Lexeme {
        plus, minus, mul, div, mod, num, open, close, var, end, unaryMinus, abs, square;

        public boolean isBinary() {
            return (this.ordinal() <= 4);
        }

        public boolean isFunction() {
            return this == abs || this == square;
        }

    }

    private Lexeme currentLexeme;
    private String s, variableName;
    private int pos, balance;
    private T num;
    private TypeBuilder<T> builder;

    public CheckedParser(TypeBuilder<T> builder) {
        this.builder = builder;
    }

    private AnyExpression firstPriority() throws Exception {
        AnyExpression ret = null;
        switch (currentLexeme) {
            case num:
                ret = new Const<>(num);
                break;
            case minus:
                currentLexeme = Lexeme.unaryMinus;
                nextLexeme();
                if (currentLexeme == Lexeme.num) {
                    ret = new Const<>(num);
                } else {
                    return new UnaryMinus<>(firstPriority());
                }
                break;
            case open:
                nextLexeme();
                ret = thirdPriority();
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
        }
        nextLexeme();
        return ret;
    }

    private AnyExpression secondPriority() throws Exception {
        AnyExpression ret = firstPriority();
        while (currentLexeme == Lexeme.mul || currentLexeme == Lexeme.div || currentLexeme == Lexeme.mod) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = (old == Lexeme.mul) ? (new Multiply(ret, firstPriority())) : (old == Lexeme.div) ? (new Divide(ret, firstPriority())) : (new Module(ret, firstPriority()));
        }
        return ret;
    }

    private AnyExpression thirdPriority() throws Exception {
        AnyExpression ret = secondPriority();
        while (currentLexeme == Lexeme.plus || currentLexeme == Lexeme.minus) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = (old == Lexeme.plus) ? (new Add(ret, secondPriority())) : (new Subtract(ret, secondPriority()));
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
                case "mod":
                    currentLexeme = Lexeme.mod;
                    break;
                case "abs":
                    currentLexeme = Lexeme.abs;
                    break;
                case "square":
                    currentLexeme = Lexeme.square;
                    break;
                default:
                    if (!nextToken.equals("x") && !nextToken.equals("y") && !nextToken.equals("z")) {
                        throw new SyntaxException("undefined variable: " + nextToken);
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
                    currentLexeme = Lexeme.mul;
                    break;
                case '/':
                    currentLexeme = Lexeme.div;
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
                case '#':
                    currentLexeme = Lexeme.end;
                    break;
                default:
                    throw new SyntaxException("undefined character at pos: " + pos);
            }
        }
        if (previousLexeme != null && previousLexeme.isBinary()) {
            if (currentLexeme != Lexeme.unaryMinus && currentLexeme != Lexeme.minus && currentLexeme != Lexeme.open && currentLexeme != Lexeme.num && currentLexeme != Lexeme.var) {
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

    private T findNum() throws Exception {
        int begin = pos;
        while (pos < s.length() && builder.build().rightCharacter(s.charAt(pos))) {
            pos++;
        }
        T res = builder.build().parse((currentLexeme == Lexeme.unaryMinus ? "-" : "") + s.substring(begin, pos--));
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
        this.s = s + "#";
        nextLexeme();
        AnyExpression ret = thirdPriority();
        if (balance != 0 || currentLexeme != Lexeme.end) {
            throw new SyntaxException("Error");
        }
        return ret;
    }
}
