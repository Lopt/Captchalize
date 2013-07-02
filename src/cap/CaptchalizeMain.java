package cap;

import cap.img.CompoundImage;

public class CaptchalizeMain {

	public static void main(String[] arguments) {
        CommandLineInterpreter interpreter = new CommandLineInterpreter();

        String[] args = new String[]{ "-y=reCaptcha", "A", "B", "C" };

        interpreter.run(args);
	}

	IResult proceedImage(CompoundImage image) {
		return this.proceedImage(image, new CaptchaSystemDefault("default"));
	}
	

	IResult proceedImage(CompoundImage image, ICaptchaSystem system) {
		return null;
	}

}
