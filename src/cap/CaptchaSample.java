package cap;

import cap.db.jpa.Managers;
import cap.img.CaptchaImage;
import cap.img.CompoundImage;
import cap.systems.UnknownCaptchaSystem;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CaptchaSample {
    private cap.db.jpa.CaptchaSample model = null;

    private CaptchaImage initializeImage = null;
    private ICaptchaSystem system = null;

    private LinkedList<ResultPart> results = new LinkedList<ResultPart>();

    public CaptchaSample(CaptchaImage initializeImage) {
        this(initializeImage, UnknownCaptchaSystem.findSystem(initializeImage), null);
    }

    public CaptchaSample(CaptchaImage initializeImage, String systemName) {
        this(initializeImage, UnknownCaptchaSystem.findSystem(systemName), null);
    }

    public CaptchaSample(CaptchaImage initializeImage, ICaptchaSystem system) {
        this(initializeImage, system, null);
    }

    public CaptchaSample(CaptchaImage initializeImage, ICaptchaSystem system, URL address) {
        assert initializeImage != null;
        assert system != null;

        this.model = Managers.captchaSampleManager.create(initializeImage.toByteArray(), system.getModel(), address);
        this.initializeImage = initializeImage;
        this.system = system;
    }

    public cap.db.jpa.CaptchaSample getModel() {
        return this.model;
    }

    public void setModel(final cap.db.jpa.CaptchaSample model) {
        this.model = model;
    }

    public CaptchaImage getInitializeImage() {
        return this.initializeImage;
    }

    public ICaptchaSystem getSystem() {
        return this.system;
    }

    public FunctionPipeline getFunctionPipeline() throws ProcessException {
        assert this.system != null;

        return this.system.getFunctionPipeline(this);
    }

    public LinkedList<ResultPart> getResultParts() {
        return this.results;
    }

    public void addResultPart(ResultPart part) {
        this.results.add(part);
    }

    public ResultPart getResultPart(int index) {
        return this.results.get(index);
    }

    public IResult getResult() {
        return null;
    }

    public CompoundImage getImageResult() {
        return null;
    }
}
