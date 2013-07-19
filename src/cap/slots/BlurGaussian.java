package cap.slots;

import cap.ISlotFunction;
import cap.ProcessException;
import cap.ResultPart;
import cap.SlotFunction;

import cap.img.Blur;
import cap.img.CompoundImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class BlurGaussian extends SlotFunction<CompoundImage, CompoundImage> implements ISlotFunction<CompoundImage, CompoundImage> {

    @Override
    public String getClassName() {
        return "BlurGaussian";
    }

    @Override
    public ResultPart<CompoundImage> execute(CompoundImage image) throws ProcessException {
        cap.db.jpa.slots.BlurGaussian data = this.getData();

        double size = data.getSize();
        return new ResultPart<CompoundImage>( Blur.gaussian(image, size) );
    }
}
