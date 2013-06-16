package cap;

import cap.CaptchaSystem;

public class Captchalize {

	public static void main(String[] args) {
		
	}

	IResult proceedImage(CompoundImage image) {
		return this.proceedImage(image, new CaptchaSystem("UNKNOWN"));
	}
	

	IResult proceedImage(CompoundImage image, CaptchaSystem system) {
		return null;
	}

}
