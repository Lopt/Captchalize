package cap;

import cap.img.CompoundImage;
import cap.systems.UnknownCaptchaSystem;

public class CaptchalizeMain {

	public static void main(String[] arguments) {
        CommandLineInterpreter interpreter = new CommandLineInterpreter();
        interpreter.run(arguments);
	}

	IResult proceedImage(CompoundImage image) {
		return this.proceedImage(image, new UnknownCaptchaSystem());
	}
	

	IResult proceedImage(CompoundImage image, ICaptchaSystem system) {
		return null;
	}

}
