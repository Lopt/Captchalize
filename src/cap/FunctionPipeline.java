package cap;

import cap.db.jpa.Managers;
import cap.db.jpa.Slot;
import cap.db.jpa.SlotNotFoundException;

import java.util.Iterator;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class FunctionPipeline {
    private cap.db.jpa.FunctionPipeline model = null;
    private String name = "";
    private Iterator<Slot> currentSlot = null;

    public FunctionPipeline(String name) {
        this.name = name;
        this.model = Managers.functionPipelineManager.get(name);
    }

    public FunctionPipeline(String name, boolean build) {
        this(name);

        if (build && this.model == null) {
            this.model = Managers.functionPipelineManager.create(name);
        }
    }

    public boolean register(String name, ISlotFunction function) {
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
        }

        return true;
    }

    public boolean unregister(String name) {
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
        return this.currentSlot.hasNext();
    }

    public <T> ISlotFunction<T> next() {
        if (currentSlot == null) {
            this.currentSlot = this.model.getSlots().iterator();
        }

        Slot slot = this.currentSlot.next();
        SlotFunction<T> function = null;

        try {
            function = (SlotFunction<T>)Class.forName("cap.slots." + slot.getClassName()).newInstance();
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
