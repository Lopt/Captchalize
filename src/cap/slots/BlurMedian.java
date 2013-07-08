package cap.slots;

import cap.ISlotFunction;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.Blur;
import cap.img.CompoundImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class BlurMedian extends SlotFunction<CompoundImage, CompoundImage> implements ISlotFunction<CompoundImage, CompoundImage> {

    @Override
    public String getClassName() {
        return "BlurMedian";
    }

    @Override
    public ResultPart<CompoundImage> execute(CompoundImage image) {
        //cap.db.jpa.slots.BlurMedian data = this.getModel().getFunctionData();

        return new ResultPart<CompoundImage>( Blur.median(image) );
    }
}
