package Evaluate.Type;

import java.math.BigInteger;

/**
 * Created by Илнар on 07.04.2015.
 */
public class OByte implements ONumber<OByte> {
    private byte value;

    public OByte(byte value) {
        this.value = value;
    }

    public OByte(int value) {
        this.value = (byte)value;
    }

    @Override
    public OByte parse(String s) {
        return new OByte(Byte.parseByte(s));
    }

    @Override
    public OByte multiply(OByte num) {
        return new OByte(value * num.value);
    }

    @Override
    public OByte add(OByte num) {
        return new OByte(value + num.value);
    }

    @Override
    public OByte subtract(OByte num) {
        return new OByte(value - num.value);
    }

    @Override
    public OByte divide(OByte num) {
        return new OByte(value / num.value);
    }

    @Override
    public OByte module(OByte num) {
        return new OByte(value % num.value);
    }

    @Override
    public OByte negate() {
        return new OByte(-value);
    }

    @Override
    public OByte abs() {
        return (value < 0) ? negate() : this;
    }

    @Override
    public OByte square() {
        return this.multiply(this);
    }

    @Override
    public boolean rightCharacter(char c) {
        return Character.isDigit(c);
    }

    @Override
    public Object value() {
        return value;
    }
}
