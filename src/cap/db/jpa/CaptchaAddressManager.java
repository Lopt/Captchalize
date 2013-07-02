package cap.db.jpa;

import java.net.URL;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
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

        CaptchaAddress address = new CaptchaAddress();

        address.setWebsite(Managers.websiteManager.getOrCreate(url.getHost()));
        address.setUrl(url);
        address.setDate(new Date());

        this.add(address);

        return address;
    }

    public CaptchaAddress getOrCreate(URL url) {
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<CaptchaAddress> query = cb.createQuery(CaptchaAddress.class);
        Root<CaptchaAddress> root = query.from(CaptchaAddress.class);

        CaptchaAddress tmp = new CaptchaAddress();
        tmp.setWebsite(Managers.websiteManager.getOrCreate(url.getHost()));
        tmp.setUrl(url);
        tmp.setDate(new Date());

        query.where(cb.and(
            cb.equal(root.get("website").get("id"), tmp.getWebsite().getId()),
            cb.equal(root.get("urlProtocol"), tmp.getUrlProtocol()),
            cb.equal(root.get("urlPath"), tmp.getUrlPath()),
            cb.equal(root.get("urlPort"), tmp.getUrlPort()),
            cb.equal(root.get("urlParams"), tmp.getUrlParams())
        ));

        CaptchaAddress address = this.get(query);
        if (address == null) {
            this.add(tmp);
            return tmp;
        } else {
            return address;
        }
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
