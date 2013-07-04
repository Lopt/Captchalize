package cap.db.jpa.slots;

import cap.db.jpa.ISlotFunctionData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class BlurMedian implements ISlotFunctionData {

    @Id
    @GeneratedValue
    private long id = 0;
    private double size = 0;

    public BlurMedian() {

    }

    @Override
    public long getId() {
        return this.id;
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(final double size) {
        this.size = size;
    }
}
