package cap.test;

import java.util.LinkedList;

import javax.swing.SwingUtilities;

import ij.ImagePlus;
import ij.io.Opener;

import cap.ResultPart;
import cap.gui.DebugGui;
import cap.img.CaptchaImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class TestDebugGui {
    public static void test() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DebugGui gui = new DebugGui();

                Opener opener = new Opener();
                ImagePlus image1 = opener.openImage("img/openedbook.png");
                ImagePlus image2 = opener.openImage("img/closedbook.png");

                LinkedList<ResultPart> results = new LinkedList<ResultPart>();
                results.add(new ResultPart<CaptchaImage>(new CaptchaImage(image1)));
                results.add(new ResultPart<CaptchaImage>(new CaptchaImage(image2)));

                gui.setResultData(results);
            }
        });
    }
}
