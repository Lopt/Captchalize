package cap.gui;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import cap.CaptchaSample;
import cap.ResultPart;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class GuiRunner implements Runnable {
    private ConcurrentLinkedQueue<CaptchaSample> samples = new ConcurrentLinkedQueue<CaptchaSample>();
    private LinkedList<ResultPart> parts = null;

    public GuiRunner(ConcurrentLinkedQueue<CaptchaSample> samples) {
        this.samples = samples;
    }

    public GuiRunner(LinkedList<ResultPart> parts) {
        this.parts = parts;
    }

    @Override
    public void run() {
        DebugGui gui = new DebugGui();
        CaptchaSample sample = this.samples.poll();
        if (sample != null) {
            gui.setResultData(sample.getResultParts());
        } else if (!this.parts.isEmpty()) {
            gui.setResultData(this.parts);
        }
    }
}
