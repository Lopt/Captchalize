package cap;

import cap.img.CompoundImage;

import java.util.List;

public class CaptchaSample {
    private cap.db.jpa.CaptchaSample model = null;
	
	public CaptchaSample(ICaptchaSystem system) {
		this.system = system;
	}

    public cap.db.jpa.CaptchaSample getModel() {
        return this.model;
    }

    public void setModel(final cap.db.jpa.CaptchaSample model) {
        this.model = model;
    }

    public IResult getResult() {
		return null;
	}
	
	public CompoundImage getImageResult() {
		return null;
	}
	
	public List<ResultPart> getImages() {
		return null;
	}
	
	private ICaptchaSystem system;
	private List<ResultPart> results;
	//functionPipeline;
	
}
