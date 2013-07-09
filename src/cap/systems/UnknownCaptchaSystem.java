package cap.systems;

import java.util.Collection;

import cap.CaptchaSample;
import cap.ICaptchaSystem;
import cap.db.jpa.CaptchaSystem;
import cap.db.jpa.Managers;
import cap.img.CaptchaImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class UnknownCaptchaSystem implements ICaptchaSystem {
    private String name = "Unknown";
    private CaptchaSystem model = null;

    public static <T extends ICaptchaSystem> T findSystem(String name) {
        cap.db.jpa.CaptchaSystem systemData = Managers.captchaSystemManager.get(name);

        if (systemData == null) {
            UnknownCaptchaSystem unknownSystem = new UnknownCaptchaSystem(name);
            unknownSystem.setModel(Managers.captchaSystemManager.create(name));
            return (T)unknownSystem;
        }

        T system = null;
        try {
            system = (T)Class.forName("cap.systems." + systemData.getName()).newInstance();
        } catch (ClassNotFoundException exception) {
            return null;
        } catch (IllegalAccessException exception) {
            return null;
        } catch (InstantiationException exception) {
            return null;
        }

        system.setModel(systemData);

        return system;
    }

    public static <T extends ICaptchaSystem> T findSystem(CaptchaImage captchaImage) {
        Collection<CaptchaSystem> captchaSystems = Managers.captchaSystemManager.findAll();

        T system = null;
        float highestMatch = 0;
        for (CaptchaSystem systemData : captchaSystems) {
            try {
                T newSystem = (T) Class.forName("cap.systems." + systemData.getName()).newInstance();
                float match = newSystem.isCaptcha(captchaImage);
                if (system == null || match >= highestMatch) {
                    highestMatch = match;
                    system = newSystem;
                }

            } catch (ClassNotFoundException exception) {
            } catch (IllegalAccessException exception) {
            } catch (InstantiationException exception) {
            }
        }
        return system;
    }

    public UnknownCaptchaSystem() {
        this.name = "default";
    }

    public UnknownCaptchaSystem(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        if (this.model == null) {
            return this.name;
        } else {
            return this.model.getName();
        }
    }

    public void setName(final String name) {
        this.name = name;
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
        CaptchaSample sample = new CaptchaSample(this);
        //sample.setImage(image);

        return sample;
    }

    @Override
    public float isCaptcha(final CaptchaImage image) {
        return 0;
    }
}

