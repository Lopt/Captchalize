package cap;

import java.util.List;

public class Captcha {
	
	public Captcha(CaptchaSystem system) {
		this.system = system;
	}
	
	
	public IResult getResult() {
		return null;
	}
	
	public CompoundImage getImageResult() {
		return null;
	}
	
	public List<IResultPart> getImages() {
		return null;
	}
	
	private CaptchaSystem system;
	private List<IResultPart> results;
	//functionPipeline;
	
}
