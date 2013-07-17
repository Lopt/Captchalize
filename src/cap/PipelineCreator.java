package cap;

import cap.db.jpa.CaptchaSystem;
import cap.slots.IntensityRemove;
import cap.slots.MorphDilate;
import cap.slots.OCRTesseract;

/**
 *
 */
public class PipelineCreator {

    public static void phpbb(CaptchaSystem model) {
        try {
            FunctionPipeline pipeline = new FunctionPipeline("phpbb greyscale", true);

            IntensityRemove firstFunction = new IntensityRemove();
            cap.db.jpa.slots.IntensityRemove data = firstFunction.getModel().getFunctionData();
            data.setBeginInterval(100);

            pipeline.register("delete grey", firstFunction);
            pipeline.register("dilate", new MorphDilate());
            pipeline.register("ocr", new OCRTesseract());
        }
        catch (ProcessException exception) { }
        catch (IllegalRegisterException exception) { }
    }

    public static void reCaptcha(CaptchaSystem model) {
        try {
            FunctionPipeline pipeline = new FunctionPipeline("recaptcha greyscale (test)", true);

            pipeline.register("delete grey", new IntensityRemove());
            pipeline.register("dilate", new MorphDilate());
            pipeline.register("ocr", new OCRTesseract());

        }
        catch (ProcessException exception) { }
        catch (IllegalRegisterException exception) { }
    }
}
