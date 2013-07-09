package cap;

import java.util.concurrent.ConcurrentLinkedQueue;

import cap.db.jpa.ServerOrder;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class CaptchalizeRunner implements Runnable {
    private static CaptchalizeRunner instance = new CaptchalizeRunner();

    Thread mainThread = null;
    boolean exit = false;
    private ConcurrentLinkedQueue<CaptchaSample> captchaSampleQueue = null;

    public static CaptchalizeRunner getInstance() {
        return instance;
    }

    public void addCaptchaSample(CaptchaSample sample) {
        this.captchaSampleQueue.add(sample);
    }

    @Override
    public void run() {
        while (!exit) {
            this.processNextCaptchaSample();

            try {
                Thread.sleep(500);
            } catch (InterruptedException exception) {
                this.exit = true;
            }
        }
    }

    private void processNextCaptchaSample() {
        CaptchaSample sample = this.captchaSampleQueue.poll();
        this.processCaptchaSample(sample);

        //ServerOrder serverOrder = sample.getModel().getServerOrder(); TODO
        //serverOrder.toProgress();
    }

    private void processCaptchaSample(CaptchaSample sample) {
        System.out.println(sample.toString()); // TODO
    }

    private CaptchalizeRunner() {
        this.captchaSampleQueue = new ConcurrentLinkedQueue<CaptchaSample>();

        mainThread = new Thread(this, "Captchalize Runner");
        mainThread.start();
    }
}
