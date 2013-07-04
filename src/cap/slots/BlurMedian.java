package cap.slots;

import cap.ISlotFunction;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.Blur;
import cap.img.CompoundImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class BlurMedian extends SlotFunction<CompoundImage> implements ISlotFunction<CompoundImage> {

    @Override
    public String getClassName() {
        return "BlurMedian";
    }

    @Override
    public ResultPart<CompoundImage> execute(CompoundImage image) {
        cap.db.jpa.slots.BlurMedian data = this.getModel().getFunctionData();

        double size = data.getSize();
        return new ResultPart<CompoundImage>( Blur.median(image, size) );
    }
}
