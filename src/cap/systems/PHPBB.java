package cap.systems;

import cap.CaptchaSample;
import cap.ICaptchaSystem;
import cap.db.jpa.CaptchaSystem;
import cap.img.CaptchaImage;

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
    public CaptchaSample createCaptcha(final CaptchaImage image) {
        return null;
    }

    @Override
    public float isCaptcha(final CaptchaImage image) {
        return 1;
    }
}
