package cap.test;

import java.sql.Statement;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;

import cap.db.IDataBaseProxy;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class TestDataBaseProxy {

    protected static void addData(IDataBaseProxy proxy) throws Exception {
        Statement st = proxy.getStatement();
        st.executeUpdate("INSERT INTO testdata (id, testvalue) VALUES (1, 'test');");

        ResultSet rs = st.executeQuery("select * from testdata");
        assertEquals(true, rs.next());

        assertEquals(1, rs.getObject(1));
        assertEquals("test", rs.getObject(2));
    }

    protected static void updateData(IDataBaseProxy proxy) throws Exception {
        Statement st = proxy.getStatement();
        st.executeUpdate("UPDATE testdata SET testvalue='foo' WHERE id=1");

        ResultSet rs = st.executeQuery("select * from testdata");
        assertEquals(true, rs.next());

        assertEquals(1, rs.getObject(1));
        assertEquals("foo", rs.getObject(2));
    }

    protected static void deleteData(IDataBaseProxy proxy) throws Exception {
        Statement st = proxy.getStatement();
        st.executeUpdate("DELETE FROM testdata WHERE id=1");

        ResultSet rs = st.executeQuery("select * from testdata");
        assertEquals(false, rs.next());
    }
}
