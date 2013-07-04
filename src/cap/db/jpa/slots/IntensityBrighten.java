package cap.db.jpa.slots;

import cap.db.jpa.ISlotFunctionData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class IntensityBrighten implements ISlotFunctionData {

    @Id
    @GeneratedValue
    private long id = 0;
    private int value = 0;

    public IntensityBrighten() {

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
