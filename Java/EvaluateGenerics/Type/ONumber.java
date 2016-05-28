package Evaluate.Type;

import java.math.BigInteger;

/**
 * Created by Илнар on 05.04.2015.
 */
public interface ONumber<T> {
    public T parse(String s);
    public T multiply(T num);
    public T add(T num);
    public T subtract(T num);
    public T divide(T num);
    public T module(T num);
    public T negate();
    public T abs();
    public T square();
    public boolean rightCharacter(char c);
    public String toString();
    public Object value();
}
