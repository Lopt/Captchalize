package cap;

import java.net.URL;

import cap.db.jpa.CaptchaSystem;
import cap.img.CaptchaImage;
import org.jsoup.nodes.Document;

public interface ICaptchaSystem {
    public String getName();

    public CaptchaSystem getModel();
    public void setModel(CaptchaSystem model);

    public float isCaptcha(CaptchaImage image);

    public FunctionPipeline getFunctionPipeline(CaptchaSample sample) throws ProcessException;
    public void createPipelines();
    public URL findCaptcha(final Document doc);
}
