package cap.img;

import java.util.ArrayList;
import cap.img.CaptchaImage;

public class CompoundImage {

    public CompoundImage() {
    }

    public CaptchaImage getImage() {
		return this.getImage(0);
	}

	public CaptchaImage getImage(final int part) {
		return this.images.get(part);
	}
	
	public void appendImage(final CaptchaImage image) {
		images.add(image);
	}
	
	public CaptchaImage getMergedImage() {
		// todo: merge Images
		return null;
	}

    public ArrayList<CaptchaImage> getImages() {
        return images;
    }

    @Override
    public CompoundImage clone() {
        CompoundImage compoundImage = new CompoundImage();
        for (CaptchaImage captchaImage : this.images) {
            compoundImage.appendImage(captchaImage.clone());
        }
        return compoundImage;
    }

	private ArrayList<CaptchaImage> images;
}
