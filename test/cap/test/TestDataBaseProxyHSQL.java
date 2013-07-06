package cap.test;

import cap.db.DataBaseProxyHSQL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@RunWith(JUnit4.class)
public class TestDataBaseProxyHSQL extends TestDataBaseProxy {

    private DataBaseProxyHSQL proxy = new DataBaseProxyHSQL();

    @Before
    public void initProxy() {
        this.proxy.setDatabaseName("file:db/hsql_test.db");
    }

    @Test
    public void createHSQLConnection() throws Exception {
        try {
            assertEquals(true, this.proxy.connect());
        } finally {
            assertEquals(true, this.proxy.disconnect());
        }
    }

    @Test
    public void changeHSQLData() throws Exception {
        this.proxy.connect();

        TestDataBaseProxy.addData(this.proxy);
        TestDataBaseProxy.updateData(this.proxy);
        TestDataBaseProxy.deleteData(this.proxy);

        this.proxy.disconnect();
    }
}
