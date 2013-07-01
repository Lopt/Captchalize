package cap;

import cap.img.CompoundImage;

public class Captchalize {

	public static void main(String[] args) {
		// parameter: --server --image=IMAGE_PATH --url=URL --system=SYSTEM
		
	}

	IResult proceedImage(CompoundImage image) {
		return this.proceedImage(image, new CaptchaSystem("UNKNOWN"));
	}
	

	IResult proceedImage(CompoundImage image, CaptchaSystem system) {
		return null;
	}

}
