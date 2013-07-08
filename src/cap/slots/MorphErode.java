package cap.slots;

import cap.ISlotFunction;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.CompoundImage;
import cap.img.Morph;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class MorphErode extends SlotFunction<CompoundImage, CompoundImage> implements ISlotFunction<CompoundImage, CompoundImage> {

    @Override
    public String getClassName() {
        return "MorphErode";
    }

    @Override
    public ResultPart<CompoundImage> execute(CompoundImage image) {
        //cap.db.jpa.slots.MorphErode data = this.getModel().getFunctionData();

        return new ResultPart<CompoundImage>(Morph.erode(image));
    }
}
