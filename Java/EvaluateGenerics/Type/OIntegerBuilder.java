package Evaluate.Type;

/**
 * Created by Илнар on 07.04.2015.
 */
public class OIntegerBuilder implements TypeBuilder<OInteger> {
    @Override
    public OInteger build() {
        return new OInteger();
    }

    @Override
    public OInteger build(int x) {
        return new OInteger(x);
    }
}
