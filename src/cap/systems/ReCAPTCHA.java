package cap.systems;

import cap.CaptchaSample;
import cap.FunctionPipeline;
import cap.ICaptchaSystem;
import cap.IllegalRegisterException;
import cap.ProcessException;
import cap.RunArguments;
import cap.db.jpa.CaptchaSystem;
import cap.img.CaptchaImage;
import cap.slots.IntensityRemove;
import cap.slots.MorphDilate;
import cap.slots.OCRTesseract;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class ReCAPTCHA implements ICaptchaSystem {
    private CaptchaSystem model = null;

    @Override
    public String getName() {
        if (this.model == null) {
            return "reCAPTCHA";
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
        return new FunctionPipeline("ReCaptcha_Default");
    }

    @Override
    public void createPipelines() {
        try {
            FunctionPipeline pipeline = new FunctionPipeline("phpbb greyscale", true);
            IntensityRemove firstFunction = new IntensityRemove();
            cap.db.jpa.slots.IntensityRemove data = firstFunction.getModel().getFunctionData();
            data.setBeginInterval(100);

            pipeline.register("delete grey", firstFunction);
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
