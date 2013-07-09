package cap;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class IllegalRegisterException extends CaptchalizeException {

    public IllegalRegisterException() {}

    public IllegalRegisterException(final String message) {
        super(message);
    }
}
