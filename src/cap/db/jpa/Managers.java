package cap.db.jpa;

import javax.persistence.EntityManager;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class Managers {
    public static CaptchaAddressManager   captchaAddressManager   = null;
    public static CaptchaImageManager     captchaImageManager     = null;
    public static CaptchaSampleManager    captchaSampleManager    = null;
    public static CaptchaSystemManager    captchaSystemManager    = null;
    public static FunctionPipelineManager functionPipelineManager = null;
    public static SlotManager             slotManager             = null;
    public static WebsiteManager          websiteManager          = null;

    public static void createManagers(EntityManager manager) {
        captchaAddressManager = new CaptchaAddressManager(manager);
        captchaImageManager = new CaptchaImageManager(manager);
        captchaSampleManager = new CaptchaSampleManager(manager);
        captchaSystemManager = new CaptchaSystemManager(manager);
        functionPipelineManager = new FunctionPipelineManager(manager);
        slotManager = new SlotManager(manager);
        websiteManager = new WebsiteManager(manager);
    }

    public static void destroyManagers() {
        captchaAddressManager = null;
        captchaImageManager = null;
        captchaSampleManager = null;
        captchaSystemManager = null;
        functionPipelineManager = null;
        slotManager = null;
        websiteManager = null;
    }

    private Managers() {}
}
