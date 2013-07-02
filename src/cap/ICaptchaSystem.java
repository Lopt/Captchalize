package cap;

import cap.img.CaptchaImage;

public interface ICaptchaSystem {

    public String getName();
    public void setName(String name);

    public boolean isCaptcha(CaptchaImage image);
    public CaptchaSample createCaptcha(CaptchaImage image);
}
