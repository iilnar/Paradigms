package Evaluate.Type;

/**
 * Created by Илнар on 07.04.2015.
 */
public interface TypeBuilder<T extends ONumber<T>>  {
    T build();
    T build(int x);
}
