package cap.db.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class Slot implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long id = 0;

    private String className  = "";
    private long   foreignKey = 0;

    public Slot() {}

    @Override
    public long getId() {
        return this.id;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public long getForeignKey() {
        return this.foreignKey;
    }

    public void setForeignKey(final long foreignKey) {
        this.foreignKey = foreignKey;
    }

    public <T extends ISlotFunction> T getFunction() {
        try {
            return Managers.slotManager.getFunction(this.className, this.foreignKey);
        } catch (SlotNotFoundException exception) {
            return null;
        }
    }
}
