package Evaluate.Exception;

/**
 * Created by Илнар on 25.03.2015.
 */
public abstract class Exception extends RuntimeException{
    final private String message;

    public Exception(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
