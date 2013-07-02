package cap.test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import cap.db.DataBaseProxyHSQL;
import cap.db.jpa.Managers;
import cap.db.jpa.CaptchaAddress;
import cap.db.jpa.CaptchaAddressManager;
import cap.db.jpa.Website;
import cap.db.jpa.WebsiteManager;
import cap.db.jpa.ExampleSlotFunction;
import cap.db.jpa.Slot;
import cap.db.jpa.SlotManager;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@RunWith(JUnit4.class)
public class TestDataBaseJPA {

    private DataBaseProxyHSQL proxy = new DataBaseProxyHSQL();

    @PersistenceUnit
    private EntityManagerFactory emf = null;
    private EntityManager        em  = null;

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
    }

    @Test
    public void createManager() {
        assertNotNull(this.em);
    }

    @Test
    public void addWebsite() {
        WebsiteManager manager = Managers.websiteManager;

        long beforeSize = manager.getCount();

        this.em.getTransaction().begin();
        manager.create("John Doe");
        manager.create("Jane Doe");
        this.em.getTransaction().commit();

        List<Website> resultList = (List<Website>) manager.findAll();
        long resultSize = manager.getCount();

        assertEquals(resultSize, resultList.size());
        assertEquals(beforeSize + 2, resultSize);

        String name = resultList.get((int) beforeSize).getHostname();
        assertEquals("John Doe", name);

        name = resultList.get((int) beforeSize + 1).getHostname();
        assertEquals("Jane Doe", name);
    }

    @Test
    public void addCaptchaUrl() throws Exception {
        WebsiteManager wm = Managers.websiteManager;
        CaptchaAddressManager cam = Managers.captchaAddressManager;

        URL url = new URL("http://www.google.com/recaptcha/learnmore?test=1&test=2");

        long beforeSizeWebsite = wm.getCount();
        long beforeSizeAddress = cam.getCount();

        this.em.getTransaction().begin();
        cam.create(url);
        this.em.getTransaction().commit();

        long afterSizeWebsite = wm.getCount();

        assertEquals(beforeSizeWebsite, afterSizeWebsite);

        List<CaptchaAddress> resultList = (List<CaptchaAddress>) cam.findAll();
        CaptchaAddress address = resultList.get((int) beforeSizeAddress);

        assertEquals("http", address.getUrlProtocol());
        assertEquals("www.google.com", address.getWebsite().getHostname());
        assertEquals("www", address.getUrlSubDomain());
        assertEquals("/recaptcha/learnmore", address.getUrlPath());
        assertEquals("test=1&test=2", address.getUrlParams());
        assertEquals(80, address.getUrlPort());
        assertEquals(
            new SimpleDateFormat("yyyy-mm-dd").format(new Date()),
            new SimpleDateFormat("yyyy-mm-dd").format(address.getDate())
        );
    }

    @Test
    public void getSlotFunction() throws Exception {
        SlotManager sfm = Managers.slotManager;

        this.em.getTransaction().begin();
        ExampleSlotFunction slotFunction = new ExampleSlotFunction();
        slotFunction.setTestValue("Test");
        this.em.persist(slotFunction);

        Slot slot = sfm.create("ExampleSlotFunction", slotFunction.getId());
        this.em.getTransaction().commit();

        assertEquals("ExampleSlotFunction", slot.getClassName());
        assertEquals(slotFunction.getId(), slot.getForeignKey());

        ExampleSlotFunction function = sfm.getFunction("ExampleSlotFunction", slotFunction.getId());
        assertNotNull(function);
        assertEquals("Test", function.getTestValue());

        function = slot.getFunction();
        assertNotNull(function);
        assertEquals("Test", function.getTestValue());
    }

    @Test
    public void getOrCreateCaptchaAddress() throws Exception {
        WebsiteManager wm = Managers.websiteManager;
        CaptchaAddressManager cam = Managers.captchaAddressManager;

        URL url = new URL("http://www.captcha.net/");

        long beforeSize = cam.getCount();

        this.em.getTransaction().begin();
        cam.getOrCreate(url);
        this.em.getTransaction().commit();

        long afterSize = cam.getCount();

        assertEquals(beforeSize, afterSize);
    }
}
