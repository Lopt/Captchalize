package cap.slots;

import cap.ISlotFunction;
import cap.ResultPart;
import cap.SlotFunction;

import cap.img.Blur;
import cap.img.CompoundImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class BlurGaussian extends SlotFunction<CompoundImage> implements ISlotFunction<CompoundImage> {

    @Override
    public String getClassName() {
        return "BlurGaussian";
    }

    @Override
    public ResultPart<CompoundImage> execute(CompoundImage image) {
        cap.db.jpa.slots.BlurGaussian data = this.getModel().getFunctionData();

        return new ResultPart<CompoundImage>( Blur.gaussian(image) );
    }
}