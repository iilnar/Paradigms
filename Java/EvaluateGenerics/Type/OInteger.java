package Evaluate.Type;

import Evaluate.Exception.EvaluateException;

import java.math.BigInteger;

/**
 * Created by Илнар on 05.04.2015.
 */
public class OInteger implements ONumber<OInteger> {
    private int value;

    public OInteger() {}

    public OInteger(int value) {
        this.value = value;
    }

    public OInteger(String s) {
        this.value = Integer.parseInt(s);
    }

    @Override
    public OInteger parse(String s) {
        return new OInteger(java.lang.Integer.parseInt(s));
    }

    @Override
    public OInteger multiply(OInteger num) {
        return new OInteger(value * num.value);
    }

    @Override
    public OInteger add(OInteger num) {
        return new OInteger(value + num.value);
    }

    @Override
    public OInteger subtract(OInteger num) {
        return new OInteger(value - num.value);
    }

    @Override
    public OInteger divide(OInteger num) {
        if (num.value == 0) {
            throw new EvaluateException("Division by zero");
        }
        return new OInteger(value / num.value);
    }

    @Override
    public OInteger module(OInteger num) {
        return new OInteger(value % num.value);
    }

    @Override
    public OInteger negate() {
        return new OInteger(-value);
    }

    @Override
    public OInteger abs() {
        if (value < 0) {
            return new OInteger(-value);
        } else {
            return new OInteger(value);
        }
    }

    @Override
    public OInteger square() {
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
