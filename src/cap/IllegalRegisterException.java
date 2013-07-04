package cap;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class IllegalRegisterException extends IllegalAccessError {

    public IllegalRegisterException() {}

    public IllegalRegisterException(final String message) {
        super(message);
    }
}
