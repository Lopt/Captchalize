package cap.test;

import static org.junit.Assert.assertEquals;

import cap.db.DataBaseProxyMySQL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@RunWith(JUnit4.class)
public class TestDataBaseProxyMySQL {

    private DataBaseProxyMySQL proxy = new DataBaseProxyMySQL();

    @Before
    public void initProxy() {
        this.proxy.setServerName("85.119.157.128");
        this.proxy.setServerPort(3306);
        this.proxy.setDatabaseName("test");
    }

    @Test
    public void createMySQLConnection() throws Exception {
        try {
            assertEquals(true, this.proxy.connect("test", "mNx7E8BAhHSh33MV"));
        } finally {
            assertEquals(true, this.proxy.disconnect());
        }
    }

    @Test
    public void changeSQLiteData() throws Exception {
        this.proxy.connect("test", "mNx7E8BAhHSh33MV");

        TestDataBaseProxy.addData(this.proxy);
        TestDataBaseProxy.updateData(this.proxy);
        TestDataBaseProxy.deleteData(this.proxy);

        this.proxy.disconnect();
    }
}
