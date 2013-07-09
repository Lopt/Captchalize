package cap.db.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class ServerOrderManager extends CaptchalizeEntityManager<ServerOrder>
    implements ICaptchalizeEntityManager<ServerOrder>  {

    public ServerOrderManager(final EntityManager manager) {
        super(manager);
    }

    public ServerOrder createFromImages(Set<CaptchaImage> images) {
        CaptchaSampleManager manager = Managers.captchaSampleManager;
        Set<CaptchaSample> samples = new HashSet<CaptchaSample>();

        for (CaptchaImage image : images) {
            samples.add(manager.create(image));
        }

        return this.create(samples);
    }

    public ServerOrder create(Set<CaptchaSample> samples) {
        ServerOrder serverOrder = new ServerOrder();
        serverOrder.setCaptchaSamples(samples);
        return serverOrder;
    }

    @Override
    public ServerOrder find(final Object primaryKey) {
        return this.find(ServerOrder.class, primaryKey);
    }

    @Override
    public Collection<ServerOrder> findAll() {
        return this.findAll(ServerOrder.class);
    }

    @Override
    public long getCount() {
        return this.getCount(ServerOrder.class);
    }

    @Override
    public long getCount(final Predicate whereClause) {
        return this.getCount(ServerOrder.class, whereClause);
    }
}
