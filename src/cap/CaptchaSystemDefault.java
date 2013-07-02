package cap;

import cap.ICaptchaSystem;
import cap.db.jpa.CaptchaSystem;
import cap.img.CaptchaImage;

public class CaptchaSystemDefault implements ICaptchaSystem {

    private CaptchaSystem model = null;

    public CaptchaSystemDefault() {
    }

    public CaptchaSystemDefault(String name) {
    }

    @Override
    public String getName() {
        assert this.model != null;

        return this.model.getName();
    }

    @Override
    public void setName(final String name) {
        assert this.model != null;

        this.model.setName(name);
    }

    @Override
    public boolean isCaptcha(final CaptchaImage image) {
        return true;
    }

    @Override
    public CaptchaSample createCaptcha(final CaptchaImage image) {
        CaptchaSample sample = new CaptchaSample(this);
        //sample.setImage(image);

        return sample;
    }
}

