package cap;

import cap.db.jpa.Slot;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */

public interface ISlotFunction<T> {
    public long getId();
    public String getName();

    public Slot getModel();

    public boolean create(String name);
    public boolean create(Slot slot);

    public ResultPart<T> execute(T data);
}
