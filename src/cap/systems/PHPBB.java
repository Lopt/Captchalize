package cap.systems;

import cap.CaptchaSample;
import cap.FunctionPipeline;
import cap.ICaptchaSystem;
import cap.IllegalRegisterException;
import cap.ProcessException;
import cap.RunArguments;
import cap.db.jpa.CaptchaSystem;
import cap.img.CaptchaImage;
import cap.slots.BlurGaussian;
import cap.slots.BlurMedian;
import cap.slots.ConvertToCompoundImage;
import cap.slots.IntensityRemove;
import cap.slots.IntensityShade;
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
        return new FunctionPipeline("phpbb greyscale median");
    }

    @Override
    public void createPipelines() {
        try {
            {
                FunctionPipeline pipeline = new FunctionPipeline("phpbb greyscale", true);
                pipeline.register("compound", new ConvertToCompoundImage());

                IntensityRemove firstFunction = new IntensityRemove();
                pipeline.register("delete grey", firstFunction);
                cap.db.jpa.slots.IntensityRemove data = firstFunction.getData();
                data.setBeginInterval(90);

                pipeline.register("dilate", new MorphDilate());
                pipeline.register("ocr", new OCRTesseract());
            }
            {
                FunctionPipeline pipeline = new FunctionPipeline("phpbb greyscale median", true);
                pipeline.register("compound", new ConvertToCompoundImage());

                IntensityRemove firstFunction = new IntensityRemove();
                pipeline.register("delete11grey", firstFunction);
                cap.db.jpa.slots.IntensityRemove data = firstFunction.getData();
                data.setBeginInterval(255);

                {
                    BlurMedian gaussian = new BlurMedian();
                    pipeline.register("blur 1", gaussian);
                    cap.db.jpa.slots.BlurMedian dataBlur = gaussian.getData();
                    //dataBlur.setSize(1);

                    IntensityShade shade = new IntensityShade();
                    pipeline.register("shade 1", shade);
                    cap.db.jpa.slots.IntensityShade dataShade = shade.getData();
                    dataShade.setValue(160);

                    IntensityRemove remove = new IntensityRemove();
                    pipeline.register("delete grey 2", remove);
                    cap.db.jpa.slots.IntensityRemove dataRemove = firstFunction.getData();
                    dataRemove.setBeginInterval(180);
                }
                {
                    BlurGaussian gaussian = new BlurGaussian();
                    pipeline.register("blur 2", gaussian);
                    cap.db.jpa.slots.BlurGaussian dataBlur = gaussian.getData();
                    dataBlur.setSize(2);


                    IntensityShade shade = new IntensityShade();
                    pipeline.register("shade 2", shade);
                    cap.db.jpa.slots.IntensityShade dataShade = shade.getData();
                    dataShade.setValue(160);

                    IntensityRemove remove = new IntensityRemove();
                    pipeline.register("delete grey 3", remove);
                    cap.db.jpa.slots.IntensityRemove dataRemove = firstFunction.getData();
                    dataRemove.setBeginInterval(180);

                }

                BlurGaussian gaussian = new BlurGaussian();
                pipeline.register("blur 2", gaussian);
                cap.db.jpa.slots.BlurGaussian dataBlur = gaussian.getData();
                dataBlur.setSize(1);

                pipeline.register("ocr", new OCRTesseract());
            }


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
