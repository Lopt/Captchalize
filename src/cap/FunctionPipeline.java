package cap;

import cap.db.jpa.Managers;
import cap.db.jpa.Slot;

import java.util.Iterator;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class FunctionPipeline {
    private cap.db.jpa.FunctionPipeline model = null;
    private boolean buildMode = false;
    private Iterator<Slot> currentSlot = null;
    private String name = "";
    private int currentId = 0;

    public FunctionPipeline(String name) throws ProcessException {
        this(name, false);
    }

    public FunctionPipeline(String name, boolean buildMode) throws ProcessException {
        this.name = name;
        this.buildMode = buildMode;

        if (buildMode) {
            this.model = Managers.functionPipelineManager.getOrCreate(name);
        } else {
            this.model = Managers.functionPipelineManager.get(name);
        }

        if (this.model == null) {
            throw new ProcessException();
        }
    }

    public boolean register(String name, ISlotFunction function) throws IllegalRegisterException {
        if (!this.buildMode) {
            throw new IllegalRegisterException("Register usable only at build mode.");
        }

        assert this.model != null;
        assert this.currentSlot == null;

        Slot slot = Managers.slotManager.find(this.model, name);
        if (slot == null) {
            if (!function.create(function.getClassName())) {
                return false;
            }

            slot = function.getModel();
            slot.setFunctionPipeline(this.model);
            slot.setName(name);

            this.model.getSlots().add(slot);
        } else {
            function.create(slot);
        }

        slot.setNumber(this.currentId);
        this.currentId += 1;

        return true;
    }

    public boolean unregister(String name) throws IllegalRegisterException {
        if (!this.buildMode) {
            throw new IllegalRegisterException("Unregister usable only at build mode.");
        }

        assert this.model != null;
        assert this.currentSlot == null;

        Slot slot = Managers.slotManager.find(this.model, name);
        if (slot != null) {
            this.model.getSlots().remove(slot);
            return true;
        }

        return false;
    }

    public boolean hasNext() {
        if (this.currentSlot == null) {
            this.currentSlot = this.model.getSlots().iterator();
        }

        return this.currentSlot.hasNext();
    }

    public <Input, Output> ISlotFunction<Input, Output> next() {
        assert this.model != null;
        assert this.currentSlot != null : "You have to check hasNext() first.";

        Slot slot = this.currentSlot.next();
        SlotFunction<Input, Output> function = null;

        try {
            function = (SlotFunction<Input, Output>)Class.forName("cap.slots." + slot.getClassName()).newInstance();
        } catch(ClassNotFoundException exception) {
            return null;
        } catch (IllegalAccessException exception) {
            return null;
        } catch (InstantiationException exception) {
            return null;
        }

        function.create(slot);
        return function;
    }
}
