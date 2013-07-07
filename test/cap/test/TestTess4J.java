package cap.test;

import net.sourceforge.tess4j.TessAPI1Test;
import net.sourceforge.tess4j.TessAPITest;
import net.sourceforge.tess4j.Tesseract1Test;
import net.sourceforge.tess4j.TesseractTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TesseractTest.class,
    TessAPITest.class,
    Tesseract1Test.class,
    TessAPI1Test.class
})
public class TestTess4J {}
