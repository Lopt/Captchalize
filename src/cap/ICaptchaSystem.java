package cap;

import cap.db.jpa.CaptchaSystem;
import cap.img.CaptchaImage;

public interface ICaptchaSystem {
    public String getName();

    public CaptchaSystem getModel();
    public void setModel(CaptchaSystem model);

    public boolean isCaptcha(CaptchaImage image);
    public CaptchaSample createCaptcha(CaptchaImage image);
}
