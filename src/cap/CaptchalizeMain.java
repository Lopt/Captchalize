package cap;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

import cap.http.CaptchalizeHttpServer;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class CaptchalizeMain {

	public static void main(String[] arguments) {
        CommandLineInterpreter interpreter = new CommandLineInterpreter();
        interpreter.run(arguments);

        RunArguments args = RunArguments.getInstance();

        Appender defaultAppender = new ConsoleAppender(new SimpleLayout());
        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());

        loggers.add(LogManager.getRootLogger());
        for ( Logger logger : loggers ) {
            logger.addAppender(defaultAppender);
            logger.setLevel(RunArguments.getInstance().isDebugMode() ? Level.DEBUG : Level.OFF);
        }

        if (args.isServerMode()) {
            CaptchalizeHttpServer server = CaptchalizeHttpServer.create(new InetSocketAddress(8080), 4);
            server.start();
        }
	}
}
