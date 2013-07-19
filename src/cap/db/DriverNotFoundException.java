package cap.db;

import cap.CaptchalizeException;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class DriverNotFoundException extends CaptchalizeException {

    public DriverNotFoundException() {
        super();
    }

    public DriverNotFoundException(final String message) {
        super(message);
    }
}
