package cap.db;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class DataBaseProxySQLite extends DataBaseProxy implements IDataBaseProxy {

    public DataBaseProxySQLite() {
        this.setDatabaseType(
            "org.sqlite.JDBC",
            "jdbc:sqlite:"
        );
    }

    public boolean connect() throws DriverNotFoundException {
        return super.connect("", "");
    }
}
