package cap;

import java.util.concurrent.ConcurrentLinkedQueue;

import cap.db.jpa.ServerOrder;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class CaptchalizeRunner implements Runnable {
    private static CaptchalizeRunner instance = new CaptchalizeRunner();

    private Thread mainThread = null;
    private boolean exit = false;
    private boolean endless = false;
    private ConcurrentLinkedQueue<CaptchaSample> captchaSampleQueue = null;

    public static CaptchalizeRunner getInstance() {
        return instance;
    }

    public void addCaptchaSample(CaptchaSample sample) {
        this.captchaSampleQueue.add(sample);
    }

    public boolean isEndless() {
        return this.endless;
    }

    public void setEndless(final boolean endless) {
        this.endless = endless;
    }

    @Override
    public void run() {
        while (!this.exit) {

            if (this.hasNextCaptchaSample()) {
                this.processNextCaptchaSample();

            } else if (!this.endless) {
                this.exit = true;

            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException exception) {
                    this.exit = true;
                }
            }
        }
    }

    public void start() {
        this.mainThread.start();
        this.run();
    }

    synchronized
    public void stop() {
        this.exit = true;
    }

    public void join() {
        if (this.exit) {
            return;
        }

        try {
            this.mainThread.join();
        } catch (InterruptedException exception) {
            this.exit = true;
        }
    }

    private boolean hasNextCaptchaSample() {
        return !this.captchaSampleQueue.isEmpty();
    }

    private void processNextCaptchaSample() {
        CaptchaSample sample = this.captchaSampleQueue.poll();
        if (sample == null) {
            return;
        }

        assert sample.getModel() != null;

        if (this.processCaptchaSample(sample)) {
            ServerOrder serverOrder = sample.getModel().getServerOrder();
            if (serverOrder != null) {
                serverOrder.toProgress();
            }
        }
    }

    private boolean processCaptchaSample(CaptchaSample sample) {
        try {
            FunctionPipeline pipeline = sample.getFunctionPipeline();
            if (pipeline.hasNext()) {

                ISlotFunction<Object, Object> function = pipeline.next();
                ResultPart output = function.execute(sample.getInitializeImage());

                while (pipeline.hasNext()) {
                    sample.addResultPart(output);
                    function = pipeline.next();
                    output = function.execute(output.getData());
                }

                sample.addResultPart(output);
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
        this.mainThread = new Thread(this, "Captchalize Runner");
    }
}
