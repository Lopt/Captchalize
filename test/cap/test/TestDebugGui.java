package cap.test;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.SwingUtilities;

import cap.CaptchaSample;
import cap.gui.GuiRunner;
import cap.img.CompoundImage;
import ij.ImagePlus;
import ij.io.Opener;

import cap.ResultPart;
import cap.gui.DebugGui;
import cap.img.CaptchaImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class TestDebugGui {

    public static void main(String[] arguments) {
        Opener opener = new Opener();
        LinkedList<ResultPart> parts = new LinkedList<ResultPart>();

        for (int i = 0; i < 7; i++) {
            ImagePlus image1 = opener.openImage(String.format("testdata/examples/phpbb_%d.png", i));
            parts.add(new ResultPart<CompoundImage>(new CompoundImage(new CaptchaImage(image1))));
        }

        SwingUtilities.invokeLater(new GuiRunner(parts));
    }
}
