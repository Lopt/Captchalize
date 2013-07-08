package cap.slots;

import cap.ISlotFunction;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.CompoundImage;
import cap.img.Morph;
import cap.img.OCR;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class OCRTesseract extends SlotFunction<CompoundImage, String> implements ISlotFunction<CompoundImage, String> {

    @Override
    public String getClassName() {
        return "OCRTesseract";
    }

    @Override
    public ResultPart<String> execute(CompoundImage image) {
        //cap.db.jpa.slots.MorphErode data = this.getModel().getFunctionData();

        return new ResultPart<String>(OCR.tesseract(image));
    }
}
