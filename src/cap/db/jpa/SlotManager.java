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
public class SlotManager extends CaptchalizeEntityManager<Slot>
    implements ICaptchalizeEntityManager<Slot> {

    public SlotManager(final EntityManager manager) {
        super(manager);
    }

    public Slot create(String className, long key) {
        Slot slot = new Slot();
        slot.setClassName(className);
        slot.setForeignKey(key);

        this.add(slot);

        return slot;
    }

    public <T extends ISlotFunction> T getFunction(String className, long key) throws SlotNotFoundException {
        T slotFunction = null;

        try {
            // throws ClassNotFoundException
            Class cls = Class.forName(Slot.class.getPackage().getName() + "." + className);

            CriteriaBuilder cb = this.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(cls);
            Root<T> root = query.from(cls);
            query.where(cb.equal(root.get("id"), key));

            // throws IndexOutOfBoundsException
            slotFunction = this.getEntityManager().createQuery(query).getResultList().get(0);

        } catch (ClassNotFoundException exception) {
            throw new SlotNotFoundException("Slot class not defined: " + Slot.class.getPackage().getName() + "." + className);
        } catch (IndexOutOfBoundsException exception) {
            throw new SlotNotFoundException("No parameters for slot " + key + " in the database.");
        }

        return slotFunction;
    }
    @Override
    public Slot find(final Object primaryKey) {
        return super.find(Slot.class, primaryKey);
    }

    @Override
    public Collection<Slot> findAll() {
        return super.findAll(Slot.class);
    }

    @Override
    public long getCount() {
        return super.getCount(Slot.class);
    }

    @Override
    public long getCount(final Predicate whereClause) {
        return super.getCount(Slot.class, whereClause);
    }
}
