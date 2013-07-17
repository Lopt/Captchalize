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
        while (!this.exit) {
            if (this.hasNextCaptchaSample()) {
                this.processNextCaptchaSample();
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException exception) {
                    this.exit = true;
                }
            }
        }
    }

    private boolean hasNextCaptchaSample() {
        return !this.captchaSampleQueue.isEmpty();
    }

    private void processNextCaptchaSample() {
        CaptchaSample sample = this.captchaSampleQueue.poll();

        if (this.processCaptchaSample(sample)) {
            ServerOrder serverOrder = sample.getModel().getServerOrder();
            serverOrder.toProgress();
        }
    }

    private boolean processCaptchaSample(CaptchaSample sample) {
        try {
            FunctionPipeline pipeline = sample.getFunctionPipeline();

            ISlotFunction<Object, Object> function = pipeline.next();
            ResultPart output = function.execute(sample.getInitializeImage());

            while (pipeline.hasNext()) {
                sample.addResultPart(output);
                function = pipeline.next();
                output = function.execute(output);
            }
        } catch (ProcessException exception) {
            if (RunArguments.getInstance().isDebugMode()) {
                exception.printStackTrace();
            }
            
            return false;
        }

        return true;
    }

    private CaptchalizeRunner() {
        this.captchaSampleQueue = new ConcurrentLinkedQueue<CaptchaSample>();

        mainThread = new Thread(this, "Captchalize Runner");
        mainThread.start();
    }
}
