package cap.slots;

import cap.ISlotFunction;
import cap.ResultPart;
import cap.SlotFunction;
import cap.img.CompoundImage;
import cap.img.Intensity;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class IntensityRemove extends SlotFunction<CompoundImage> implements ISlotFunction<CompoundImage> {

    @Override
    public String getClassName() {
        return "IntensityRemove";
    }

    @Override
    public ResultPart<CompoundImage> execute(CompoundImage image) {
        cap.db.jpa.slots.IntensityRemove data = this.getModel().getFunctionData();

        int beginInterval    = data.getBeginInterval();
        int endInterval      = data.getEndInterval();
        int standardValue    = data.getStandardValue();
        boolean includeBegin = data.getIncludeBegin();
        boolean includeEnd   = data.getIncludeEnd();

        return new ResultPart<CompoundImage>(Intensity.remove(image, beginInterval, endInterval, standardValue, includeBegin, includeEnd));
    }
}
