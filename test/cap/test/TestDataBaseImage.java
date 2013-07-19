package cap.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import ij.io.FileSaver;
import ij.io.ImageWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ij.ImagePlus;

import cap.db.DataBaseProxyHSQL;
import cap.db.jpa.CaptchaAddressManager;
import cap.db.jpa.CaptchaImageManager;
import cap.db.jpa.Managers;
import cap.img.CaptchaImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@RunWith(JUnit4.class)
public class TestDataBaseImage {
    private DataBaseProxyHSQL proxy = new DataBaseProxyHSQL();

    @PersistenceUnit
    private EntityManagerFactory emf = null;
    private EntityManager em  = null;

    @Before
    public void initEmfAndEm() throws Exception {
        this.proxy.setDatabaseName("file:testdata/db/hsql_test.db");
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
    public void imageSaveAndLoad() {
        CaptchaImageManager cim = Managers.captchaImageManager;

        CaptchaImage beforeCaptchaImage = new CaptchaImage("testdata/examples/phpbb_0.php");
        byte[] data = beforeCaptchaImage.toByteArray();

        long beforeCount = cim.getCount();

        this.em.getTransaction().begin();
        cap.db.jpa.CaptchaImage beforeDbImage = cim.create(data);
        this.em.getTransaction().commit();

        long id = beforeDbImage.getId();
        long afterCount = cim.getCount();

        assertTrue(beforeCount + 1 == afterCount);

        cap.db.jpa.CaptchaImage afterDbImage = cim.find(id);
        assertArrayEquals(beforeDbImage.getSourceImageData(), afterDbImage.getSourceImageData());

        CaptchaImage afterCaptchaImage = new CaptchaImage(afterDbImage.getSourceImageData());

        ImagePlus beforeSaveImage = beforeCaptchaImage.getImage();
        ImagePlus afterSaveImage = afterCaptchaImage.getImage();

        assertArrayEquals(beforeSaveImage.getPixel(7, 3), afterSaveImage.getPixel(7, 3));
        assertArrayEquals(beforeSaveImage.getPixel(6, 8), afterSaveImage.getPixel(6, 8));
        assertArrayEquals(beforeSaveImage.getPixel(12, 15), afterSaveImage.getPixel(12, 15));

        FileSaver saver = new FileSaver(afterSaveImage);
        saver.saveAsPng("testdata/imagetest.png");
    }
}

