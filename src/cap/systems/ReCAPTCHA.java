package cap.systems;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
        return new FunctionPipeline("recaptcha greyscale");
    }

    @Override
    public void createPipelines() {
        try {
            FunctionPipeline pipeline = new FunctionPipeline("recaptcha greyscale", true);
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

    @Override
    public URL findCaptcha(final Document doc) {
        // TODO: finding the correct ID and give it back

        Element noscript = doc.select("noscript").get(0);
        Element iframe = noscript.select("iframe").get(0);

        try {
            URL captchaURL = new URL(iframe.attr("src"));

            Document captchaHTML = Jsoup.connect(iframe.attr("src")).get();
            Element captchaImage = captchaHTML.select("img").get(0);
            String imageRelativeURL = captchaImage.attr("src");

            URL imageURL = new URL(captchaURL.getProtocol(), captchaURL.getHost(), "/" + imageRelativeURL);
            return imageURL;

        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
        }

        return null;
    }
}
