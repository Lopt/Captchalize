package cap.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.Collection;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class CaptchaSampleManager extends CaptchalizeEntityManager<CaptchaSample>
    implements ICaptchalizeEntityManager<CaptchaSample> {

    public CaptchaSampleManager(EntityManager manager) {
        super(manager);
    }

    public CaptchaSample create() {
        CaptchaSample sample = new CaptchaSample();

        this.add(sample);

        return sample;
    }

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
}
