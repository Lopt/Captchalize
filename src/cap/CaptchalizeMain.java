package cap;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.SwingUtilities;

import cap.db.DriverNotFoundException;
import cap.db.IDataBaseProxy;
import cap.db.jpa.Managers;
import cap.systems.PHPBB;
import cap.systems.ReCAPTCHA;
import cap.systems.UnknownCaptchaSystem;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import cap.gui.GuiRunner;
import cap.http.CaptchalizeHttpServer;
import cap.img.CaptchaImage;

public class CaptchalizeMain {

    public static void createPipelines(EntityManager manager) {
        UnknownCaptchaSystem system0 = new UnknownCaptchaSystem();
        PHPBB                system1 = new PHPBB();
        ReCAPTCHA            system2 = new ReCAPTCHA();

        manager.getTransaction().begin();
        system0.createPipelines();
        system1.createPipelines();
        system2.createPipelines();
        manager.getTransaction().commit();
    }

	public static void main(String[] arguments) {
        ConcurrentLinkedQueue<CaptchaSample> samples = new ConcurrentLinkedQueue<CaptchaSample>();

        CommandLineInterpreter interpreter = new CommandLineInterpreter();
        if (!interpreter.run(arguments)) {
            return;
        }

        RunArguments args = RunArguments.getInstance();
        args.setCaptchaSystem("PHPBB");
        args.setServerMode(true);
        args.setServerPort(8080);
        args.setImages(new String[] { "testdata/examples/phpbb_0.png" });

        IDataBaseProxy proxy = args.getDatabaseProxy();
        try {
            proxy.connect(args.getDatabaseUser(), args.getDatabasePassword());
        } catch (DriverNotFoundException exception) {
            exception.printStackTrace();
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Captchalize", proxy.getHibernateConfig());
        EntityManager         em = emf.createEntityManager();

        Managers.createManagers(em);

        Appender defaultAppender = new ConsoleAppender(new SimpleLayout());
        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());

        loggers.add(LogManager.getRootLogger());
        for (Logger logger : loggers) {
            logger.addAppender(defaultAppender);
            logger.setLevel(RunArguments.getInstance().isDebugMode() ? Level.DEBUG : Level.OFF);
        }

        // TODO only at first run
        createPipelines(em);

        CaptchalizeRunner runner = CaptchalizeRunner.getInstance();

        if (args.isServerMode()) {
            runner.setEndless(true);

            CaptchalizeHttpServer server = CaptchalizeHttpServer.create(
                new InetSocketAddress(args.getServerPort()),
                args.getMaxServerConnections()
            );
            server.start();

        } else if (args.getFindOn() != null) {

        } else if (!args.getImages().isEmpty()) {
            em.getTransaction().begin();
            for (CaptchaImage image : args.getImages()) {
                samples.add(new CaptchaSample(image, args.getCaptchaSystem()));
            }
            em.getTransaction().commit();
        }

        for (CaptchaSample sample : samples) {
            runner.addCaptchaSample(sample);
        }

        runner.start();
        runner.join();

        proxy.disconnect();
        em.close();
        emf.close();

        Managers.destroyManagers();

        if (args.isServerMode()) {
            return;
        }

        if (args.isDebugGui()) {
            SwingUtilities.invokeLater(new GuiRunner(samples));
        }
	}
}
