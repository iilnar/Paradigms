package Evaluate.Type;

import java.math.BigInteger;

/**
 * Created by Илнар on 05.04.2015.
 */
public class OFloat implements ONumber<OFloat> {
    private float value;

    public OFloat() {}

    public OFloat(float value) {
        this.value = value;
    }

    @Override
    public OFloat parse(String s) {
        return new OFloat(java.lang.Float.parseFloat(s));
    }

    @Override
    public OFloat multiply(OFloat num) {
        return new OFloat(value * num.value);
    }

    @Override
    public OFloat add(OFloat num) {
        return new OFloat(value + num.value);
    }

    @Override
    public OFloat subtract(OFloat num) {
        return new OFloat(value - num.value);
    }

    @Override
    public OFloat divide(OFloat num) {
        return new OFloat(value / num.value);
    }

    @Override
    public OFloat module(OFloat num) {
        return new OFloat(value % num.value);
    }

    @Override
    public OFloat negate() {
        return new OFloat(-value);
    }

    @Override
    public OFloat abs() {
        if (value < 0) {
            return negate();
        } else {
            return new OFloat(value);
        }
    }

    @Override
    public OFloat square() {
        return new OFloat(value * value);
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
