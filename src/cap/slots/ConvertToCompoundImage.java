package cap.slots;

import cap.ISlotFunction;
import cap.ProcessException;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.Blur;
import cap.img.CaptchaImage;
import cap.img.CompoundImage;
import cap.img.Convert;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class ConvertToCompoundImage extends SlotFunction<CaptchaImage, CompoundImage> implements ISlotFunction<CaptchaImage, CompoundImage> {

    @Override
    public String getClassName() {
        return "ConvertToCompoundImage";
    }

    @Override
    public ResultPart<CompoundImage> execute(CaptchaImage image) throws ProcessException {
        //cap.db.jpa.slots.BlurMedian data = this.getModel().getFunctionData();

        return new ResultPart<CompoundImage>(Convert.toCompoundImage(image) );
    }
}
