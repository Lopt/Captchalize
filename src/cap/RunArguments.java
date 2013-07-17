package cap;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import cap.img.CaptchaImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class RunArguments {
    private static RunArguments instance = new RunArguments();

    private boolean debugGui   = false;
    private boolean debugMode  = false;
    private boolean serverMode = false;

    private long maxImageSize = 524288; // 512 kByte
    private long maxRequestSize = 3145728; // 3 MByte

    private ICaptchaSystem captchaSystem = null;
    private LinkedList<CaptchaImage> images = null;
    private URL findOn = null;

    public static RunArguments getInstance() {
        return instance;
    }

    public ICaptchaSystem getCaptchaSystem() {
        return this.captchaSystem;
    }

    public void setCaptchaSystem(final ICaptchaSystem captchaSystem) {
        this.captchaSystem = captchaSystem;
    }

    public boolean isDebugGui() {
        return this.debugGui;
    }

    public void setDebugGui(final boolean debugGui) {
        this.debugGui = debugGui;
    }

    public boolean isDebugMode() {
        return this.debugMode;
    }

    public void setDebugMode(final boolean debugMode) {
        this.debugMode = debugMode;
    }

    public Collection<CaptchaImage> getImages() {
        return this.images;
    }

    public void setImages(final LinkedList<CaptchaImage> images) {
        this.images = images;
    }

    public void setImages(final String[] imagePaths) {
        this.images = new LinkedList();

        for (String path : imagePaths) {
            this.images.add(new CaptchaImage(new File(path)));
        }
    }

    public boolean isServerMode() {
        return this.serverMode;
    }

    public void setServerMode(final boolean serverMode) {
        this.serverMode = serverMode;
    }

    public URL getFindOn() {
        return this.findOn;
    }

    public void setFindOn(final URL findOn) {
        this.findOn = findOn;
    }

    public long getMaxImageSize() {
        return this.maxImageSize;
    }

    public void setMaxImageSize(final long maxImageSize) {
        this.maxImageSize = maxImageSize;
    }

    public long getMaxRequestSize() {
        return this.maxRequestSize;
    }

    public void setMaxRequestSize(final long maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    private RunArguments() {}
}
