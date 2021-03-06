package cap;

import cap.db.jpa.ISlotFunctionData;
import cap.db.jpa.Slot;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */

public interface ISlotFunction<Input, Output> {
    public long getId();
    public String getClassName();
    public String getName();

    public Slot getModel();
    public <T extends ISlotFunctionData> T getData();

    public boolean create(String name);
    public boolean create(Slot slot);

    public ResultPart<Output> execute(Input data) throws ProcessException ;
}
