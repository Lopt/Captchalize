package cap;

import cap.img.CompoundImage;

public class CaptchalizeMain {

	public static void main(String[] args) {
		// parameter: --server --image=IMAGE_PATH --url=URL --system=SYSTEM
	}

	IResult proceedImage(CompoundImage image) {
		return this.proceedImage(image, new CaptchaSystemDefault("default"));
	}
	

	IResult proceedImage(CompoundImage image, ICaptchaSystem system) {
		return null;
	}

}
