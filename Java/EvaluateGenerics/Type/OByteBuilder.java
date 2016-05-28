package Evaluate.Type;

/**
 * Created by Илнар on 07.04.2015.
 */
public class OByteBuilder implements TypeBuilder<OByte> {
    @Override
    public OByte build() {
        return new OByte(0);
    }

    @Override
    public OByte build(int x) {
        return new OByte(x);
    }
}