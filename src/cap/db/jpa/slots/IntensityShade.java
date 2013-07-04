package cap.db.jpa.slots;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import cap.db.jpa.ISlotFunctionData;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class IntensityShade implements ISlotFunctionData {

    @Id
    @GeneratedValue
    private long id = 0;
    private int value = 0;

    public IntensityShade() {

    }

    @Override
    public long getId() {
        return this.id;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(final int value) {
        this.value = value;
    }
}
