package cap;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import cap.gui.DebugGui;
import cap.http.CaptchalizeHttpServer;
import cap.img.CaptchaImage;
import ij.ImagePlus;
import ij.io.Opener;
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
        for (Logger logger : loggers) {
            logger.addAppender(defaultAppender);
            logger.setLevel(RunArguments.getInstance().isDebugMode() ? Level.DEBUG : Level.OFF);
        }

        CaptchalizeRunner runner = CaptchalizeRunner.getInstance();

        if (args.isServerMode()) {
            runner.setEndless(true);

            CaptchalizeHttpServer server = CaptchalizeHttpServer.create(new InetSocketAddress(8080), 4);
            server.start();

        } else if (args.getFindOn() != null) {

        } else if (!args.getImages().isEmpty()) {
            for (CaptchaImage image : args.getImages()) {
                runner.addCaptchaSample(new CaptchaSample(image));
            }
        }

        runner.start();
        runner.join();

        if (args.isDebugGui()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    DebugGui gui = new DebugGui();
                    gui.setResultData(<<< HIER COLLECTION MIT RESULT PARTS >>>);
                }
            });
        }
	}
}
