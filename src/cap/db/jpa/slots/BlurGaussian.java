package cap.db.jpa.slots;

import cap.db.jpa.ISlotFunctionData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class BlurGaussian implements ISlotFunctionData {

    @Id
    @GeneratedValue
    private long id = 0;

    private String testValue = "";

    public BlurGaussian() {}

    @Override
    public long getId() {
        return this.id;
    }

    public String getTestValue() {
        return this.testValue;
    }

    public void setTestValue(final String testValue) {
        this.testValue = testValue;
    }
}
