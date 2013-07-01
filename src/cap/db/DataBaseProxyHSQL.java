package cap.db;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class DataBaseProxyHSQL extends DataBaseProxy implements IDataBaseProxy {

    public DataBaseProxyHSQL() {
        this.setDatabaseType(
            "org.hsqldb.jdbcDriver",
            "jdbc:hsqldb:"
        );
    }

    public boolean connect() throws DriverNotFoundException {
        return super.connect("SA", "");
    }
}
