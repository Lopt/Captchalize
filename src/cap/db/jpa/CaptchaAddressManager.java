package cap.db.jpa;

import java.net.URL;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class CaptchaAddressManager extends CaptchalizeEntityManager<CaptchaAddress>
    implements ICaptchalizeEntityManager<CaptchaAddress> {

    public CaptchaAddressManager(EntityManager manager) {
        super(manager);
    }

    public CaptchaAddress create(URL url) {
        WebsiteManager wm = Managers.websiteManager;
        CriteriaBuilder cb = wm.getCriteriaBuilder();
        CriteriaQuery<Website> query = cb.createQuery(Website.class);
        Root<Website> root = query.from(Website.class);
        query.where(cb.equal(root.get("hostname"), url.getHost()));

        CaptchaAddress address = new CaptchaAddress();
        Website site = wm.get(query);
        if (site == null) {
            address.setWebsite(wm.create(url.getHost()));
        } else {
            address.setWebsite(site);
        }
        address.setUrl(url);
        address.setDate(new Date());

        this.add(address);

        return address;
    }

    @Override
    public CaptchaAddress find(final Object primaryKey) {
        return super.find(CaptchaAddress.class, primaryKey);
    }

    @Override
    public Collection<CaptchaAddress> findAll() {
        return super.findAll(CaptchaAddress.class);
    }

    @Override
    public long getCount() {
        return super.getCount(CaptchaAddress.class);
    }

    @Override
    public long getCount(final Predicate whereClause) {
        return super.getCount(CaptchaAddress.class, whereClause);
    }
}
