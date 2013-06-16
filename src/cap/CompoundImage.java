package cap;

import java.util.ArrayList;
import cap.CaptchaImage;

public class CompoundImage {
	
	public CaptchaImage getImage() {
		return this.getImage(0);
	}

	public CaptchaImage getImage(int part) {
		return this.images.get(part);
	}
	
	public void appendImage(CaptchaImage image) {
		images.add(image);
	}
	
	public CaptchaImage getMergedImage() {
		// todo: merge Images
		return null;
	}
	
	
	private ArrayList<CaptchaImage> images;
}
