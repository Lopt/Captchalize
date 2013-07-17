package cap.gui;

import java.util.List;

import cap.CaptchaSample;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class GuiRunner implements Runnable {
    private List<CaptchaSample> samples = null;

    public GuiRunner(List<CaptchaSample> samples) {
        this.samples = samples;
    }

    @Override
    public void run() {
        DebugGui gui = new DebugGui();
        CaptchaSample sample = samples.get(0);
        gui.setResultData(sample.getResultParts());
    }
}
