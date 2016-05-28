import java.io.*;
import java.util.StringTokenizer;

public class TaskE {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new TaskE().run();
    }

    public String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return "-1";
            }
        }
        return st.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    public long nextLong() {
        return Long.parseLong(nextToken());
    }

    double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    String nextLine() throws IOException {
        return br.readLine();
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "";
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + ".out");
            }
        } catch (Throwable e) {
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }
    void solve() {
        ExpressionParser parser = new ExpressionParser();
        double a = nextDouble(), b = nextDouble();
        String s = nextToken();
        AnyExpression expression = parser.parse("abs " + s);
        out.println((b - a) * (expression.evaluate(a) + expression.evaluate(b)) / 2);

    }
}


//////////////////////////////////////////////////////////////////////////////


class Add extends BinaryOperation {
    public Add(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public double function(double left, double right) {
        return left + right;
    }
}

interface AnyExpression extends Expression, TripleExpression {
}

abstract class AnyOperation implements AnyExpression {
    public double evaluate(double x) {
        return evaluate(x, 0, 0);
    }
}

abstract class BinaryOperation extends AnyOperation {
    protected final AnyExpression leftOperand;
    protected final AnyExpression rightOperand;

    abstract double function(double left, double right);

    public BinaryOperation(AnyExpression leftOperand, AnyExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public double evaluate(double x, double y, double z) {
        return function(leftOperand.evaluate(x, y, z), rightOperand.evaluate(x, y, z));
    }
}

class Const extends AnyOperation {
    private final double value;

    public Const(double value) {
        this.value = value;
    }

    public double evaluate(double x, double y, double z) {
        return value;
    }
}

class Divide extends BinaryOperation {
    public Divide(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public double function(double left, double right) {
        return left / right;
    }
}

interface Expression {
    double evaluate(double x);
}

class Multiply extends BinaryOperation {
    public Multiply(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public double function(double left, double right) {
        return left * right;
    }
}

class Subtract extends BinaryOperation {
    public Subtract(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public double function(double left, double right) {
        return left - right;
    }
}

interface TripleExpression {
    double evaluate(double x, double y, double z);
}

class UnaryMinus extends UnaryOperation {
    public UnaryMinus (AnyExpression child) {
        super(child);
    }

    public double function(double value) {
        return -value;
    }
}

class Abs extends UnaryOperation {
    public Abs(AnyExpression child) {
        super(child);
    }

    public double function(double value) {
        return Math.abs(value);
    }

}


class Sin extends UnaryOperation {
    public Sin(AnyExpression child) {
        super(child);
    }

    double function(double value) {
        return Math.sin(value);
    }
}

class Cos extends UnaryOperation {
    public Cos(AnyExpression child) {
        super(child);
    }

    double function(double value) {
        return Math.cos(value);
    }
}

abstract class UnaryOperation extends AnyOperation{
    protected final AnyExpression child;

    abstract double function(double value);

    public UnaryOperation(AnyExpression child) {
        this.child = child;
    }

    public double evaluate(double x, double y, double z) {
        return function(child.evaluate(x, y, z));
    }
}

class Sqrt extends UnaryOperation {
    public Sqrt(AnyExpression child) {
        super(child);
    }

    public double function(double value) {
        return Math.sqrt(value);
    }
}

class Pow extends BinaryOperation {
    public Pow(AnyExpression leftOperand, AnyExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    public double function(double left, double right) {
        return Math.pow(left, right);
    }
}


class Variable extends AnyOperation {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public double evaluate(double x, double y, double z) {
        return name.equals("x") ? x : name.equals("y") ? y : z;
    }
}



/////////////////////////////////////////////////

interface Parser {
    TripleExpression parse(String expression);
}


class ExpressionParser implements Parser{
    enum Lexeme {
        plus, minus, mul, div, num, open, close, var, sin, cos, pow, sqrt, abs
    }

    private Lexeme currentLexeme;
    private String s, variableName;
    private int pos;
    private double num;

    private AnyExpression firstPriority() {
        AnyExpression ret = null;
        if (currentLexeme == Lexeme.num) {
            ret = new Const(num);
        } else if (currentLexeme == Lexeme.minus) {
            nextLexeme();
            if (currentLexeme == Lexeme.num) {
                ret = new Const(-num);
            } else {
                return new UnaryMinus(firstPriority());
            }
        } else if (currentLexeme == Lexeme.open) {
            nextLexeme();
            ret = thirdPriority();
        } else if (currentLexeme == Lexeme.var) {
            ret = new Variable(variableName);
        } else if (currentLexeme == Lexeme.sin) {
            nextLexeme();
            return new Sin(firstPriority());
        } else if (currentLexeme == Lexeme.cos) {
            nextLexeme();
            return new Cos(firstPriority());
        } else if (currentLexeme == Lexeme.sqrt) {
            nextLexeme();
            return new Sqrt(firstPriority());
        } else if (currentLexeme == Lexeme.abs) {
            nextLexeme();
            return new Abs(firstPriority());
        }
        nextLexeme();
        return ret;
    }

    private AnyExpression qqqPriority() {
        AnyExpression ret = firstPriority();
        while (currentLexeme == Lexeme.pow) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = new Pow(ret, firstPriority());
        }
        return ret;
    }

    private AnyExpression secondPriority() {
        AnyExpression ret = qqqPriority();
        while (currentLexeme == Lexeme.mul || currentLexeme == Lexeme.div) {
            Lexeme old = currentLexeme;
            nextLexeme();
            ret = (old == Lexeme.mul) ? (new Multiply(ret, firstPriority())) : ( new Divide(ret, firstPriority()));
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

    private void nextLexeme() {
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
                case "sin":
                    currentLexeme = Lexeme.sin;
                    break;
                case "cos":
                    currentLexeme = Lexeme.cos;
                    break;
                case "sqrt":
                    currentLexeme = Lexeme.sqrt;
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
                case '^':
                    currentLexeme = Lexeme.pow;
                    break;
            }
        }
        pos++;
    }

    private double findNum() {
        int begin = pos;
        while (pos < s.length() && (Character.isDigit(s.charAt(pos)) || s.charAt(pos) == '.')) {
            pos++;
        }
        return Double.parseDouble(s.substring(begin, pos--));
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
        return thirdPriority();
    }
}

