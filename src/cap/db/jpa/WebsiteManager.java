package cap.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.Collection;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class WebsiteManager extends CaptchalizeEntityManager<Website>
    implements ICaptchalizeEntityManager<Website> {

    public WebsiteManager(final EntityManager manager) {
        super(manager);
    }

    public Website create(final String hostname) {
        Website site = new Website();
        site.setHostname(hostname);

        this.add(site);

        return site;
    }

    @Override
    public Website find(final Object primaryKey) {
        return super.find(Website.class, primaryKey);
    }

    @Override
    public Collection<Website> findAll() {
        return super.findAll(Website.class);
    }

    @Override
    public long getCount() {
        return super.getCount(Website.class);
    }

    @Override
    public long getCount(final Predicate whereClause) {
        return super.getCount(Website.class, whereClause);
    }
}
