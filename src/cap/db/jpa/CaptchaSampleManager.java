package cap.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.net.URL;
import java.util.Collection;

import cap.ICaptchaSystem;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class CaptchaSampleManager extends CaptchalizeEntityManager<CaptchaSample>
    implements ICaptchalizeEntityManager<CaptchaSample> {

    public CaptchaSampleManager(EntityManager manager) {
        super(manager);
    }

    public CaptchaSample create(CaptchaImage image) {
        assert image != null;

        CaptchaSample sample = this.create();
        sample.setCaptchaImage(image);
        return sample;
    }

    public CaptchaSample create(CaptchaAudio audio) {
        assert audio != null;

        CaptchaSample sample = this.create();
        sample.setCaptchaAudio(audio);
        return sample;
    }

    public CaptchaSample create(CaptchaText text) {
        assert text != null;

        CaptchaSample sample = this.create();
        sample.setCaptchaText(text);
        return sample;
    }

    public CaptchaSample create(byte[] imageData, CaptchaSystem system, ServerOrder order) {
        assert imageData != null;
        assert system != null;
        assert order != null;

        CaptchaSample sample = this.create();

        sample.setCaptchaImage(Managers.captchaImageManager.create(imageData));
        sample.setCaptchaSystem(system);
        sample.setServerOrder(order);

        return sample;
    }

    public CaptchaSample create(byte[] imageData, CaptchaSystem system, URL address) {
        assert imageData != null;
        assert system != null;

        CaptchaSample sample = this.create();

        sample.setCaptchaImage(Managers.captchaImageManager.create(imageData));
        sample.setCaptchaSystem(system);
        if (address == null) {
            sample.setCaptchaAddress(Managers.captchaAddressManager.create(address));
        }

        return sample;
    }

    private CaptchaSystem captchaSystem = null;
    private ServerOrder serverOrder = null;
    private CaptchaAddress captchaAddress = null;


    @Override
    public CaptchaSample find(final Object primaryKey) {
        return super.find(CaptchaSample.class, primaryKey);
    }

    @Override
    public Collection<CaptchaSample> findAll() {
        return super.findAll(CaptchaSample.class);
    }

    @Override
    public long getCount() {
        return super.getCount(CaptchaSample.class);
    }

    @Override
    public long getCount(final Predicate whereClause) {
        return super.getCount(CaptchaSample.class, whereClause);
    }

    private CaptchaSample create() {
        CaptchaSample sample = new CaptchaSample();
        this.add(sample);
        return sample;
    }
}
