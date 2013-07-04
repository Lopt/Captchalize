package cap;

import cap.db.jpa.Managers;
import cap.db.jpa.Slot;
import cap.db.jpa.SlotNotFoundException;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public abstract class SlotFunction<T> implements ISlotFunction<T> {
    private Slot model = null;

    public Slot getModel() {
        return this.model;
    }

    @Override
    public boolean create(String name) {
        try {
            this.model = Managers.slotManager.create(name);
        } catch (SlotNotFoundException exception) {
            return false;
        }

        return true;
    }

    @Override
    public boolean create(Slot slot) {
        this.model = slot;
        return true;
    }

    @Override
    public String getName() {
        assert this.model != null : "Slot is not created.";

        return this.model.getName();
    }

    @Override
    public abstract String getClassName();

    @Override
    public long getId() {
        assert this.model != null : "Slot is not created.";

        return this.model.getForeignKey();
    }

    @Override
    public abstract ResultPart<T> execute(final T data);
}
