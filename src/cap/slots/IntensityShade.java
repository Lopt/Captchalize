package cap.slots;

import cap.ISlotFunction;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.Intensity;
import cap.img.CompoundImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class IntensityShade extends SlotFunction<CompoundImage, CompoundImage> implements ISlotFunction<CompoundImage, CompoundImage> {

    @Override
    public String getClassName() {
        return "IntensityShade";
    }

    @Override
    public ResultPart<CompoundImage> execute(CompoundImage image) {
        cap.db.jpa.slots.IntensityShade data = this.getModel().getFunctionData();

        int value = data.getValue();
        return new ResultPart<CompoundImage>(Intensity.shade(image, value));
    }
}
