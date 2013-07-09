package cap;

import cap.db.jpa.CaptchaSystem;
import cap.img.CaptchaImage;

public interface ICaptchaSystem {
    public String getName();

    public CaptchaSystem getModel();
    public void setModel(CaptchaSystem model);

    public float isCaptcha(CaptchaImage image);
    public FunctionPipeline getFunctionPipeline(CaptchaSample sample) throws ProcessException;
}
