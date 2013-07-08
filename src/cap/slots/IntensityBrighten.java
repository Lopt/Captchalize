package cap.slots;

import cap.ISlotFunction;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.CompoundImage;
import cap.img.Intensity;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class IntensityBrighten extends SlotFunction<CompoundImage, CompoundImage> implements ISlotFunction<CompoundImage, CompoundImage> {

    @Override
    public String getClassName() {
        return "IntensityBrighten";
    }

    @Override
    public ResultPart<CompoundImage> execute(CompoundImage image) {
        cap.db.jpa.slots.IntensityBrighten data = this.getModel().getFunctionData();

        int value = data.getValue();
        return new ResultPart<CompoundImage>(Intensity.brighten(image, value));
    }
}
