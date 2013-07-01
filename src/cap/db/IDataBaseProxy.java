package cap.db;

import java.sql.Statement;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public interface IDataBaseProxy {

    public String getServerName();
    public void setServerName(String name);

    public int getServerPort();
    public void setServerPort(int port);

    public String getDatabaseName();
    public void setDatabaseName(String name);

    public Statement getStatement();

    public boolean connect(String username, String password) throws DriverNotFoundException;
    public boolean disconnect();
}
