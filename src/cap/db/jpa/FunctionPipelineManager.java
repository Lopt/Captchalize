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
public class FunctionPipelineManager extends CaptchalizeEntityManager<FunctionPipeline>
        implements ICaptchalizeEntityManager<FunctionPipeline> {

    public FunctionPipelineManager(final EntityManager manager) {
        super(manager);
    }

    public FunctionPipeline create(String name) {
        FunctionPipeline pipeline = new FunctionPipeline();
        pipeline.setName(name);

        this.add(pipeline);

        return pipeline;
    }

    public FunctionPipeline get(String name) {
        CriteriaBuilder cb = this.getCriteriaBuilder();
        CriteriaQuery<FunctionPipeline> query = cb.createQuery(FunctionPipeline.class);
        Root<FunctionPipeline> root = query.from(FunctionPipeline.class);

        query.where(cb.equal(root.get("name"), name));

        return this.get(query);
    }

    public FunctionPipeline getOrCreate(String name) {
        FunctionPipeline pipeline = this.get(name);
        if (pipeline == null) {
            return this.create(name);
        } else {
            return pipeline;
        }
    }

    @Override
    public FunctionPipeline find(final Object primaryKey) {
        return super.find(FunctionPipeline.class, primaryKey);
    }

    @Override
    public Collection<FunctionPipeline> findAll() {
        return super.findAll(FunctionPipeline.class);
    }

    @Override
    public long getCount() {
        return super.getCount(FunctionPipeline.class);
    }

    @Override
    public long getCount(final Predicate whereClause) {
        return super.getCount(FunctionPipeline.class, whereClause);
    }
}
