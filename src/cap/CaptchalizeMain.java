package cap;

import java.net.InetSocketAddress;

import cap.http.CaptchalizeHttpServer;
import cap.img.CompoundImage;
import cap.systems.UnknownCaptchaSystem;

public class CaptchalizeMain {

	public static void main(String[] arguments) {
        CommandLineInterpreter interpreter = new CommandLineInterpreter();
        interpreter.run(arguments);

        RunArguments args = RunArguments.getInstance();
        if (args.isServerMode()) {
            CaptchalizeHttpServer server = CaptchalizeHttpServer.create(new InetSocketAddress(8080), 4);
            server.start();
        }
	}
}
