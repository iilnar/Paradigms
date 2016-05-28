package Evaluate.Type;

import Evaluate.Exception.EvaluateException;

import java.math.BigInteger;

/**
 * Created by Илнар on 05.04.2015.
 */
public class OSafeInteger implements ONumber<OSafeInteger> {
    private int value;

    public OSafeInteger() {}

    public OSafeInteger(int value) {
        this.value = value;
    }

    public OSafeInteger(String s) {
        this.value = Integer.parseInt(s);
    }

    @Override
    public OSafeInteger parse(String s) {
        return new OSafeInteger(java.lang.Integer.parseInt(s));
    }

    @Override
    public OSafeInteger multiply(OSafeInteger num) {
        if (num.value > 0 && (value > Integer.MAX_VALUE / num.value  || value < Integer.MIN_VALUE / num.value)) {
            throw new EvaluateException("Overflow");
        }
        if (num.value < -1 && (value > Integer.MIN_VALUE / num.value || value < Integer.MAX_VALUE / num.value) ) {
            throw new EvaluateException("Overflow");
        }
        if (num.value == -1 && value == Integer.MIN_VALUE) {
            throw new EvaluateException("Overflow");
        }
        return new OSafeInteger(value * num.value);
    }

    @Override
    public OSafeInteger add(OSafeInteger num) {
        if (num.value > 0 ? value > Integer.MAX_VALUE - num.value : value < Integer.MIN_VALUE - num.value) {
            throw new EvaluateException("Overflow");
        }
        return new OSafeInteger(value + num.value);
    }

    @Override
    public OSafeInteger subtract(OSafeInteger num) {
        if ((num.value > 0) ? (value < Integer.MIN_VALUE + num.value) : (value > Integer.MAX_VALUE + num.value)) {
            throw new EvaluateException("Overflow");
        }
        return new OSafeInteger(value - num.value);
    }

    @Override
    public OSafeInteger divide(OSafeInteger num) {
        if (num.value == 0) {
            throw new EvaluateException("Division by zero");
        }
        if (value == Integer.MIN_VALUE && num.value == -1) {
            throw new EvaluateException("Overflow");
        }
        return new OSafeInteger(value / num.value);
    }

    @Override
    public OSafeInteger module(OSafeInteger num) {
        return new OSafeInteger(value % num.value);
    }

    @Override
    public OSafeInteger negate() {
        if (value == Integer.MIN_VALUE) {
            throw new EvaluateException("Overflow");
        }
        return new OSafeInteger(-value);
    }

    @Override
    public OSafeInteger abs() {
        if (value == Integer.MIN_VALUE) {
            throw new EvaluateException("Overflow in abs");
        }
        if (value < 0) {
            return new OSafeInteger(-value);
        } else {
            return new OSafeInteger(value);
        }
    }

    @Override
    public OSafeInteger square() {
        return this.multiply(this);
    }

    @Override
    public boolean rightCharacter(char c) {
        return Character.isDigit(c);
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
