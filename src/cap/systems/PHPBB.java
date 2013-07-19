package cap.systems;

import cap.CaptchaSample;
import cap.FunctionPipeline;
import cap.ICaptchaSystem;
import cap.IllegalRegisterException;
import cap.ProcessException;
import cap.RunArguments;
import cap.db.jpa.CaptchaSystem;
import cap.img.CaptchaImage;
import cap.slots.ConvertToCompoundImage;
import cap.slots.IntensityRemove;
import cap.slots.MorphDilate;
import cap.slots.OCRTesseract;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class PHPBB implements ICaptchaSystem {
    private CaptchaSystem model = null;

    @Override
    public String getName() {
        if (this.model == null) {
            return "PHPBB";
        } else {
            return this.model.getName();
        }
    }

    @Override
    public CaptchaSystem getModel() {
        return this.model;
    }

    @Override
    public void setModel(final CaptchaSystem model) {
        this.model = model;
    }

    @Override
    public float isCaptcha(final CaptchaImage image) {
        return 1;
    }

    @Override
    public FunctionPipeline getFunctionPipeline(final CaptchaSample sample) throws ProcessException {
        return new FunctionPipeline("phpbb default");
    }

    @Override
    public void createPipelines() {
        try {
            FunctionPipeline pipeline = new FunctionPipeline("phpbb greyscale", true);
            pipeline.register("compound", new ConvertToCompoundImage());

            IntensityRemove firstFunction = new IntensityRemove();
            pipeline.register("delete grey", firstFunction);

            cap.db.jpa.slots.IntensityRemove data = firstFunction.getData();
            data.setBeginInterval(100);

            pipeline.register("dilate", new MorphDilate());
            pipeline.register("ocr", new OCRTesseract());

        } catch (ProcessException exception) {
            if (RunArguments.getInstance().isDebugMode()) {
                exception.printStackTrace();
            }
        } catch (IllegalRegisterException exception) {
            if (RunArguments.getInstance().isDebugMode()) {
                exception.printStackTrace();
            }
        }
    }
}
