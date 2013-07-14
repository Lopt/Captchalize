package cap.slots;

import cap.ISlotFunction;
import cap.ProcessException;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.CompoundImage;
import cap.img.OCR;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class OCRIsNumeric extends SlotFunction<String, String> implements ISlotFunction<String, String> {

    @Override
    public String getClassName() {
        return "OCRIsNumeric";
    }

    @Override
    public ResultPart<String> execute(String text) throws ProcessException {
        //cap.db.jpa.slots.MorphErode data = this.getModel().getFunctionData();

        return new ResultPart<String>(OCR.isNumeric(text));
    }
}
