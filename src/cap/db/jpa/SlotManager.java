package cap.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
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

    public Slot create(String name) throws SlotNotFoundException {
        ISlotFunctionData slotFunction = this.createSlotFunction(name);
        if (slotFunction == null) {
            throw new SlotNotFoundException();
        }

        Slot slot = new Slot();
        slot.setClassName(name);
        slot.setForeignKey(slotFunction.getId());

        this.add(slot);

        return slot;
    }

    public Slot create(String name, long key) {
        Slot slot = new Slot();
        slot.setClassName(name);
        slot.setForeignKey(key);

        this.add(slot);

        return slot;
    }

    public ISlotFunctionData createSlotFunction(String name) {
        ISlotFunctionData slotFunction = null;

        try {
            Class slotFunctionClass = Class.forName(this.getClassName(name));
            slotFunction = (ISlotFunctionData)slotFunctionClass.newInstance();

        } catch (ClassNotFoundException exception) {
            return null;
        } catch (IllegalAccessException exception) {
            return null;
        } catch (InstantiationException exception) {
            return null;
        }

        this.getEntityManager().persist(slotFunction);
        this.getEntityManager().flush();

        return slotFunction;
    }

    public Slot find(String name, long key) {
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<Slot> query = cb.createQuery(Slot.class);
        Root<Slot> root = query.from(Slot.class);

        query.where(cb.and(
            cb.equal(root.get("className"), name),
            cb.equal(root.get("foreignKey"), key)));

        return this.get(query);
    }

    public <T extends ISlotFunctionData> T getFunctionData(String name, long key) throws SlotNotFoundException {
        T slotFunction = null;

        try {
            // throws ClassNotFoundException
            Class cls = Class.forName(this.getClassName(name));

            CriteriaBuilder cb = this.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(cls);
            Root<T> root = query.from(cls);
            query.where(cb.equal(root.get("id"), key));

            // throws IndexOutOfBoundsException
            slotFunction = this.getEntityManager().createQuery(query).getResultList().get(0);

        } catch (ClassNotFoundException exception) {
            throw new SlotNotFoundException("Slot class not defined: " + this.getClassName(name));
        } catch (IndexOutOfBoundsException exception) {
            throw new SlotNotFoundException("No parameters for slot " + key + " in the database.");
        }

        return slotFunction;
    }

    public boolean existSlotFunction(String name) {
        try {
            Class cls = Class.forName(this.getClassName(name));
        } catch (ClassNotFoundException exception) {
            return false;
        }

        return true;
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

    private String getClassName(String name) {
        if (!name.startsWith("cap.")) {
            return Slot.class.getPackage().getName() + ".slots." + name;
        }

        return name;
    }
}
