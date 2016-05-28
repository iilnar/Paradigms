package Evaluate.Type;

import java.math.BigInteger;

/**
 * Created by Илнар on 05.04.2015.
 */
public class OBigInteger implements ONumber<OBigInteger> {
    private java.math.BigInteger value;

    public OBigInteger() {}

    public OBigInteger(String s) {
        value = this.parse(s).value;
    }

    public OBigInteger(java.math.BigInteger value) {
        this.value = value;
    }

    public OBigInteger(int value) {
        this.value = BigInteger.valueOf(value);
    }

    @Override
    public OBigInteger parse(String s) {
        return new OBigInteger(new java.math.BigInteger(s));
    }

    @Override
    public OBigInteger multiply(OBigInteger num) {
        return new OBigInteger(value.multiply(num.value));
    }

    @Override
    public OBigInteger add(OBigInteger num) {
        return new OBigInteger(value.add(num.value));
    }

    @Override
    public OBigInteger subtract(OBigInteger num) {
        return new OBigInteger(value.subtract(num.value));
    }

    @Override
    public OBigInteger divide(OBigInteger num) {
        return new OBigInteger(value.divide(num.value));
    }

    @Override
    public OBigInteger module(OBigInteger num) {
        return new OBigInteger(value.abs().mod(num.value.abs())).multiply(new OBigInteger(value.signum()));
    }

    @Override
    public OBigInteger negate() {
        return new OBigInteger(value.negate());
    }
//-15+ -6 % -7
    @Override
    public OBigInteger abs() {
        return new OBigInteger(value.abs());
    }

    @Override
    public OBigInteger square() {
        return new OBigInteger(value.multiply(value));
    }

    @Override
    public boolean rightCharacter(char c) {
        return Character.isDigit(c);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Object value() {
        return value;
    }
}
