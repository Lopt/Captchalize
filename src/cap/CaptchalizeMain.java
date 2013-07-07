package cap;

import java.net.InetSocketAddress;

import cap.RunArguments;
import cap.http.CaptchalizeServer;
import cap.img.CompoundImage;
import cap.systems.UnknownCaptchaSystem;

public class CaptchalizeMain {

	public static void main(String[] arguments) {
        CommandLineInterpreter interpreter = new CommandLineInterpreter();
        interpreter.run(arguments);

        RunArguments args = RunArguments.getInstance();
        if (args.isServerMode()) {
            CaptchalizeServer server = CaptchalizeServer.create(new InetSocketAddress(8080), 4);
            server.start();
        }
	}

	IResult proceedImage(CompoundImage image) {
		return this.proceedImage(image, new UnknownCaptchaSystem());
	}
	

	IResult proceedImage(CompoundImage image, ICaptchaSystem system) {
		return null;
	}

}
