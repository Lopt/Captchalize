package cap.test;

import static org.junit.Assert.assertEquals;

import cap.db.DataBaseProxySQLite;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@RunWith(JUnit4.class)
public class TestDataBaseProxySQLite extends TestDataBaseProxy {

    private DataBaseProxySQLite proxy = new DataBaseProxySQLite();

    @Before
    public void initProxy() {
        this.proxy.setDatabaseName("db/sqlite_test.db");
    }

    @Test
    public void createSQLiteConnection() throws Exception {
        try {
            assertEquals(true, this.proxy.connect());
        } finally {
            assertEquals(true, this.proxy.disconnect());
        }
    }

    @Test
    public void changeSQLiteData() throws Exception {
        this.proxy.connect();

        TestDataBaseProxy.addData(this.proxy);
        TestDataBaseProxy.updateData(this.proxy);
        TestDataBaseProxy.deleteData(this.proxy);

        this.proxy.disconnect();
    }
}
