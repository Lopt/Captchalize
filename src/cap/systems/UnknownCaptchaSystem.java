package cap.systems;

import java.util.Collection;

import cap.CaptchaSample;
import cap.FunctionPipeline;
import cap.ICaptchaSystem;
import cap.IllegalRegisterException;
import cap.ProcessException;
import cap.RunArguments;
import cap.db.jpa.CaptchaSystem;
import cap.db.jpa.Managers;
import cap.img.CaptchaImage;
import cap.slots.BlurGaussian;
import cap.slots.ConvertToCompoundImage;
import cap.slots.OCRTesseract;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class UnknownCaptchaSystem implements ICaptchaSystem {
    private String name = "Unknown";
    private CaptchaSystem model = null;

    public static <T extends ICaptchaSystem> T findSystem(String name) {
        cap.db.jpa.CaptchaSystem systemData = Managers.captchaSystemManager.get(name);

        UnknownCaptchaSystem unknownSystem = new UnknownCaptchaSystem(name);
        if (systemData == null) {
            unknownSystem.setModel(Managers.captchaSystemManager.create(name));
            return (T)unknownSystem;
        } else {
            unknownSystem.setModel(systemData);
        }

        T system = null;
        try {
            system = (T)Class.forName("cap.systems." + systemData.getName()).newInstance();
        } catch (ClassNotFoundException exception) {
            return (T)unknownSystem;
        } catch (IllegalAccessException exception) {
            return (T)unknownSystem;
        } catch (InstantiationException exception) {
            return (T)unknownSystem;
        }

        system.setModel(systemData);

        return system;
    }

    public static <T extends ICaptchaSystem> T findSystem(CaptchaImage captchaImage) {
        Collection<CaptchaSystem> captchaSystems = Managers.captchaSystemManager.findAll();

        T system = null;
        float highestMatch = 0;
        for (CaptchaSystem systemData : captchaSystems) {
            T newSystem = findSystem(systemData.getName());
            float match = newSystem.isCaptcha(captchaImage);
            if (system == null || match >= highestMatch) {
                highestMatch = match;
                system = newSystem;
            }
        }
        if (system == null) {
            system = UnknownCaptchaSystem.findSystem("Unknown");
        }
        return system;
    }

    public UnknownCaptchaSystem() {}

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
    public float isCaptcha(final CaptchaImage image) {
        return 0;
    }

    @Override
    public FunctionPipeline getFunctionPipeline(final CaptchaSample sample) throws ProcessException {
        return new FunctionPipeline("Unknown_Default");
    }

    @Override
    public void createPipelines() {
        FunctionPipeline defaultPipeline = null;

        try {
            defaultPipeline = new FunctionPipeline("Unknown_Default", true);
            defaultPipeline.register("getCompound", new ConvertToCompoundImage());
            defaultPipeline.register("OCR", new OCRTesseract());

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

