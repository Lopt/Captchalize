package cap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import cap.CaptchalizeMain;
import cap.CommandLineInterpreter;
import cap.RunArguments;
import cap.db.DataBaseProxyHSQL;
import cap.db.jpa.Managers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@RunWith(JUnit4.class)
public class TestRunArguments {
    private CommandLineInterpreter interpreter = null;
    private DataBaseProxyHSQL proxy = new DataBaseProxyHSQL();

    @PersistenceUnit
    private EntityManagerFactory emf = null;
    private EntityManager em  = null;

    @Before
    public void initEmfAndEm() throws Exception {
        this.proxy.setDatabaseName("file:testdata/db/hsql_test.db");
        this.proxy.connect();

        this.emf = Persistence.createEntityManagerFactory("Captchalize", proxy.getHibernateConfig());
        this.em = emf.createEntityManager();

        Managers.createManagers(this.em);
        this.interpreter = new CommandLineInterpreter();
    }

    @After
    public void cleanup() {
        this.proxy.disconnect();
        this.em.close();
        this.emf.close();

        Managers.destroyManagers();
    }

    @Test
    public void parseArguments_1() {
        this.interpreter.run(new String[]{"--debug", "--server-mode"});

        RunArguments args = RunArguments.getInstance();
        assertTrue(args.isDebugMode());
        assertTrue(args.isServerMode());
    }

    @Test
    public void parseArguments_2() {
        this.interpreter.run(new String[]{"testdata/img/openedbook.png", "https://www.google.de/images/srpr/logo4w.png"});

        RunArguments args = RunArguments.getInstance();
        assertTrue(args.getImages().size() > 0);
    }

    @Test
    public void parseArguments_3() {
        this.interpreter.run(new String[]{"-y=reCAPTCHA", "testdata/img/closedbook.png"});

        RunArguments args = RunArguments.getInstance();
        assertEquals("reCAPTCHA", args.getCaptchaSystem().getName());
        assertTrue(args.getImages().size() > 0);
    }
}
