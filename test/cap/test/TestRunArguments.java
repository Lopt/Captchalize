package cap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cap.CaptchalizeMain;
import cap.CommandLineInterpreter;
import cap.RunArguments;
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

    @Before
    public void initInterpreter() {
        this.interpreter = new CommandLineInterpreter();
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
        this.interpreter.run(new String[]{"--system=reCAPTCHA", "testdata/img/closedbook.png"});

        RunArguments args = RunArguments.getInstance();
        assertEquals("reCAPTCHA", args.getCaptchaSystem().getName());
        assertTrue(args.getImages().size() > 0);
    }
}
