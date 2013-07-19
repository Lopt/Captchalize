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
        ImagePlus image1 = opener.openImage("testdata/img/openedbook.png");
        ImagePlus image2 = opener.openImage("testdata/img/closedbook.png");

        LinkedList<ResultPart> parts = new LinkedList<ResultPart>();
        parts.add(new ResultPart<CompoundImage>(new CompoundImage(new CaptchaImage(image1))));
        parts.add(new ResultPart<CompoundImage>(new CompoundImage(new CaptchaImage(image2))));

        SwingUtilities.invokeLater(new GuiRunner(parts));
    }
}
