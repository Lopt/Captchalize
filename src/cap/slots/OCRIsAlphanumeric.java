package cap.slots;

import cap.ISlotFunction;
import cap.ProcessException;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.OCR;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class OCRIsAlphanumeric extends SlotFunction<String, String> implements ISlotFunction<String, String> {

    @Override
    public String getClassName() {
        return "OCRIsAlphanumeric";
    }

    @Override
    public ResultPart<String> execute(String text) throws ProcessException {
        //cap.db.jpa.slots.MorphErode data = this.getData();

        return new ResultPart<String>(OCR.isAlphanumeric(text));
    }
}
