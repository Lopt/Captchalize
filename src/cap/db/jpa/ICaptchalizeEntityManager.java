package cap.db.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public interface ICaptchalizeEntityManager<T extends ICaptchalizeEntity> {

    public EntityManager getEntityManager();

    public CriteriaBuilder getCriteriaBuilder();

    public void add(final T entity);
    public void remove(final T entity);

    public T get(CriteriaQuery<T> query);
    public Collection<T> all(CriteriaQuery<T> query);

    public T find(final Object primaryKey);
    public Collection<T> findAll();

    public long getCount();
    public long getCount(Predicate whereClause);
}