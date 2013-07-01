package cap.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.Collection;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class CaptchaImageManager extends CaptchalizeEntityManager<CaptchaImage>
    implements ICaptchalizeEntityManager<CaptchaImage> {

    public CaptchaImageManager(EntityManager manager) {
        super(manager);
    }

    public CaptchaImage create(byte[] imageData) {
        CaptchaImage image = new CaptchaImage();
        image.setSourceImageData(imageData);

        this.add(image);

        return image;
    }

    @Override
    public CaptchaImage find(Object primaryKey) {
        return super.find(CaptchaImage.class, primaryKey);
    }

    @Override
    public Collection<CaptchaImage> findAll() {
        return super.findAll(CaptchaImage.class);
    }

    @Override
    public long getCount() {
        return super.getCount(CaptchaImage.class);
    }

    @Override
    public long getCount(Predicate whereClause) {
        return super.getCount(CaptchaImage.class, whereClause);
    }
}
