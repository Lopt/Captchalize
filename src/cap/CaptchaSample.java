package cap;

import cap.img.CompoundImage;

import java.util.List;

public class CaptchaSample {
	
	public CaptchaSample(ICaptchaSystem system) {
		this.system = system;
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
