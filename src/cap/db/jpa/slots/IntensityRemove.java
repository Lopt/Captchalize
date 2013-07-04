package cap.db.jpa.slots;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import cap.db.jpa.ISlotFunctionData;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class IntensityRemove implements ISlotFunctionData {

    @Id
    @GeneratedValue
    private long id = 0;
    private int beginInterval = 0;
    private int endInterval   = 255;
    private boolean includeBegin = true;
    private boolean includeEnd   = true;

    public IntensityRemove() {

    }

    @Override
    public long getId() {
        return this.id;
    }

    public void setBeginInterval(final int beginInterval) {
        this.beginInterval = beginInterval;
    }

    public void setEndInterval(final int endInterval) {
        this.endInterval = endInterval;
    }

    public void setIncludeBegin(final boolean includeBegin) {
        this.includeBegin = includeBegin;
    }

    public void setIncludeEnd(final boolean includeEnd) {
        this.includeEnd = includeEnd;
    }

    public boolean getIncludeBegin() {
        return this.includeBegin;
    }

    public boolean getIncludeEnd() {
        return this.includeEnd;
    }

    public int getEndInterval() {
        return this.endInterval;
    }

    public int getBeginInterval() {
        return this.beginInterval;
    }


}
