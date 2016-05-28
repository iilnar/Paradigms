package Evaluate.Type;

/**
 * Created by Илнар on 07.04.2015.
 */
public class ODoubleBuilder implements TypeBuilder<ODouble> {
    @Override
    public ODouble build() {
        return new ODouble();
    }

    @Override
    public ODouble build(int x) {
        return new ODouble(x);
    }
}
