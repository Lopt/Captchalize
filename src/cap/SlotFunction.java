package cap;

import cap.db.jpa.ISlotFunctionData;
import cap.db.jpa.Managers;
import cap.db.jpa.Slot;
import cap.db.jpa.SlotNotFoundException;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public abstract class SlotFunction<Input, Output> implements ISlotFunction<Input, Output> {
    private Slot model = null;

    @Override
    public Slot getModel() {
        return this.model;
    }

    @Override
    public <T extends ISlotFunctionData> T getData() {
        assert this.model != null : String.format("You have to register the SlotFunction %s first.", this.getClassName());

        return this.model.getFunctionData();
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
    public abstract ResultPart<Output> execute(final Input data) throws ProcessException;
}
