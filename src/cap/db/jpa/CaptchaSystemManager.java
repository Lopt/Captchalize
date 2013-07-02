package cap.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class CaptchaSystemManager extends CaptchalizeEntityManager<CaptchaSystem>
    implements ICaptchalizeEntityManager<CaptchaSystem> {

    public CaptchaSystemManager(EntityManager manager) {
        super(manager);
    }

    public CaptchaSystem create(String name) {
        CaptchaSystem system = new CaptchaSystem();
        system.setName(name);

        this.add(system);

        return system;
    }

    public CaptchaSystem getOrCreate(String name) {
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<CaptchaSystem> query = cb.createQuery(CaptchaSystem.class);
        Root<CaptchaSystem> root = query.from(CaptchaSystem.class);

        query.where(cb.equal(root.get("name"), name));

        CaptchaSystem system = this.get(query);
        if (system == null) {
            return this.create(name);
        } else {
            return system;
        }
    }

    @Override
    public CaptchaSystem find(final Object primaryKey) {
        return super.find(CaptchaSystem.class, primaryKey);
    }

    @Override
    public Collection<CaptchaSystem> findAll() {
        return super.findAll(CaptchaSystem.class);
    }

    @Override
    public long getCount() {
        return super.getCount(CaptchaSystem.class);
    }

    @Override
    public long getCount(final Predicate whereClause) {
        return super.getCount(CaptchaSystem.class, whereClause);
    }
}
