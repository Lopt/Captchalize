package cap.db;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class DriverNotFoundException extends Exception {

    public DriverNotFoundException() {}

    public DriverNotFoundException(String message) {
        super(message);
    }
}
