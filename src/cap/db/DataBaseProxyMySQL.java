package cap.db;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class DataBaseProxyMySQL extends DataBaseProxy implements IDataBaseProxy {

    public DataBaseProxyMySQL() {
        this.setDatabaseType(
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://",
            "useUnicode=yes&characterEncoding=UTF-8"
        );
    }
}
