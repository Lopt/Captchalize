package cap.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import static org.junit.Assert.assertEquals;

import cap.slots.BlurGaussian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import cap.FunctionPipeline;
import cap.ISlotFunction;
import cap.db.DataBaseProxyHSQL;
import cap.db.jpa.ISlotFunctionData;
import cap.db.jpa.Managers;


/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@RunWith(JUnit4.class)
public class TestFunctionPipelines {

    private DataBaseProxyHSQL proxy = new DataBaseProxyHSQL();

    @PersistenceUnit
    private EntityManagerFactory emf = null;
    private EntityManager em  = null;

    @Before
    public void initEmfAndEm() throws Exception {
        this.proxy.setDatabaseName("file:db/hsql_test.db");
        this.proxy.connect();

        this.emf = Persistence.createEntityManagerFactory("Captchalize", proxy.getHibernateConfig());
        this.em = emf.createEntityManager();

        Managers.createManagers(this.em);
    }

    @After
    public void cleanup() {
        this.proxy.disconnect();
        this.em.close();
        this.emf.close();

        Managers.destroyManagers();
    }

    @Test
    public void createFunctionPipeline() {
        this.em.getTransaction().begin();

        FunctionPipeline pipeline = new FunctionPipeline("TestBlur");
        pipeline.load();

        pipeline.register(BlurGaussian.get());

        this.em.getTransaction().commit();

        ISlotFunction function = pipeline.next();
        assertEquals("BlurGaussian", function.getName());

        assertEquals("cap.ResultPart", function.execute(null).getClass().getName());
    }
}
