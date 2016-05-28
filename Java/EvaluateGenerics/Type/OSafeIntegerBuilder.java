package Evaluate.Type;

/**
 * Created by Илнар on 07.04.2015.
 */
public class OSafeIntegerBuilder implements TypeBuilder<OSafeInteger>{
    @Override
    public OSafeInteger build() {
        return new OSafeInteger("0");
    }

    @Override
    public OSafeInteger build(int x) {
        return new OSafeInteger(x);
    }
}
