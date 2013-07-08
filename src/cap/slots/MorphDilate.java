package cap.slots;

import cap.ISlotFunction;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.CompoundImage;
import cap.img.Morph;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class MorphDilate extends SlotFunction<CompoundImage, CompoundImage> implements ISlotFunction<CompoundImage, CompoundImage> {

    @Override
    public String getClassName() {
        return "MorphDilate";
    }

    @Override
    public ResultPart<CompoundImage> execute(CompoundImage image) {
        //cap.db.jpa.slots.MorphDilate data = this.getModel().getFunctionData();

        return new ResultPart<CompoundImage>(Morph.dilate(image));
    }
}
