package cap.db.jpa;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class SlotNotFoundException extends ClassNotFoundException {

    public SlotNotFoundException() {}

    public SlotNotFoundException(String message) {
        super(message);
    }
}
