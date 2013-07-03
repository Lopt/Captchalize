package cap.slots;

import cap.ISlotFunction;
import cap.ResultPart;
import cap.SlotFunction;
import cap.db.jpa.Slot;
import cap.img.Blur;
import cap.img.CompoundImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class BlurGaussian extends SlotFunction<CompoundImage> implements ISlotFunction<CompoundImage> {
    public static BlurGaussian get() {
        BlurGaussian function = new BlurGaussian();
        function.create("BlurGaussian");
        return function;
    }

    @Override
    public ResultPart<CompoundImage> execute(CompoundImage image) {
        cap.db.jpa.slots.BlurGaussian data = this.model.getFunctionData();

        String testvalue = data.getTestValue();

        return new ResultPart<CompoundImage>( Blur.gaussian(image) );
    }
}