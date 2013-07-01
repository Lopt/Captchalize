package cap.db.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public abstract class CaptchalizeEntityManager<T extends ICaptchalizeEntity>
    implements ICaptchalizeEntityManager<T> {

    private EntityManager manager = null;

    public CaptchalizeEntityManager(EntityManager manager) {
        assert manager != null : "Manager is null.";

        this.manager = manager;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.manager;
    }

    @Override
    public final CriteriaBuilder getCriteriaBuilder() {
        return this.manager.getCriteriaBuilder();
    }

    @Override
    public void add(final T entity) {
        this.manager.persist(entity);
    }

    @Override
    public void remove(final T entity) {
        if (entity != null) {
            this.manager.remove(entity);
        }
    }

    @Override
    public T get(CriteriaQuery<T> query) {
        try {
            return this.manager.createQuery(query).getResultList().get(0);
        }
        catch (IndexOutOfBoundsException exception) {
            return null;
        }
    }

    @Override
    public Collection<T> all(CriteriaQuery<T> query) {
        return this.manager.createQuery(query).getResultList();
    }

    @Override
    public abstract T find(final Object primaryKey);

    @Override
    public abstract Collection<T> findAll();

    @Override
    public abstract long getCount();

    @Override
    public abstract long getCount(final Predicate whereClause);

    public T find(final Class<T> entityClass, final Object primaryKey) {
        return this.manager.find(entityClass, primaryKey);
    }

    public Collection<T> findAll(final Class<T> entityClass) {
        return this.all(this.getEmptySelectQuery(entityClass));
    }

    public long getCount(final Class<T> entityClass) {
        return this.getCount(entityClass, this.getCriteriaBuilder().and());
    }

    public long getCount(final Class<T> entityClass, final Predicate whereClause) {
        CriteriaBuilder cb = this.manager.getCriteriaBuilder();

        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(entityClass);

        query.select(cb.count(root));
        query.where(whereClause);

        return this.manager.createQuery(query).getSingleResult();
    }

    private CriteriaQuery<T> getEmptySelectQuery(Class<T> entityClass) {
        CriteriaQuery<T> query = this.manager.getCriteriaBuilder().createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        return query.select(root);
    }
}
