package Evaluate.Type;

/**
 * Created by Илнар on 07.04.2015.
 */
public class OFloatBuilder implements TypeBuilder<OFloat> {
    public OFloat build() {
        return new OFloat(0.0f);
    }

    @Override
    public OFloat build(int x) {
        return new OFloat(x);
    }
}
