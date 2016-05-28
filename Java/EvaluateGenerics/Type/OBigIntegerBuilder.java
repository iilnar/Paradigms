package Evaluate.Type;

/**
 * Created by Илнар on 07.04.2015.
 */
public class OBigIntegerBuilder implements TypeBuilder<OBigInteger> {
    @Override
    public OBigInteger build() {
        return new OBigInteger("0");
    }

    @Override
    public OBigInteger build(int x) {
        return new OBigInteger(x);
    }
}
