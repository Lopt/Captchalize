package cap.systems;

import cap.CaptchaSample;
import cap.ICaptchaSystem;
import cap.img.CaptchaImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class UnknownCaptchaSystem implements ICaptchaSystem {
    private String name = "Unknown";

    public static <T extends ICaptchaSystem> T findSystem(String name) {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public CaptchaSample createCaptcha(final CaptchaImage image) {
        return null;
    }

    @Override
    public boolean isCaptcha(final CaptchaImage image) {
        return true;
    }
}
