package cap.db.jpa.slots;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import cap.db.jpa.ISlotFunctionData;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class ConvertToCompoundImage implements ISlotFunctionData {

    @Id
    @GeneratedValue
    private long id = 0;

    public ConvertToCompoundImage() {
    }

    @Override
    public long getId() {
        return this.id;
    }
}
