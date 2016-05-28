package Evaluate.Type;

import java.math.BigInteger;

/**
 * Created by Илнар on 05.04.2015.
 */
public class ODouble implements ONumber<ODouble> {
    private double value;

    public ODouble() {}

    public ODouble(double value) {
        this.value = value;
    }

    @Override
    public ODouble parse(String s) {
        return new ODouble(java.lang.Double.parseDouble(s));
    }

    @Override
    public ODouble multiply(ODouble num) {
        return new ODouble(value * num.value);
    }

    @Override
    public ODouble add(ODouble num) {
        return new ODouble(value + num.value);
    }

    @Override
    public ODouble subtract(ODouble num) {
        return new ODouble(value - num.value);
    }

    @Override
    public ODouble divide(ODouble num) {
        return new ODouble(value / num.value);
    }

    @Override
    public ODouble module(ODouble num) {
        return new ODouble(value % num.value);
    }

    @Override
    public ODouble negate() {
        return new ODouble(-value);
    }

    @Override
    public ODouble abs() {
        if (value < 0) {
            return negate();
        } else {
            return new ODouble(value);
        }
    }

    @Override
    public ODouble square() {
        return new ODouble(value * value);
    }

    @Override
    public boolean rightCharacter(char c) {
        return Character.isDigit(c) || c == '.';
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Object value() {
        return value;
    }
}
