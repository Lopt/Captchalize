package cap.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import cap.RunArguments;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class DataBaseProxy implements IDataBaseProxy {

    private String serverName   = "";
    private int    serverPort   = 0;
    private String databaseName = "";
    private String username     = "";
    private String password     = "";

    private Statement  statement  = null;
    private Connection connection = null;

    private String driverClassName = "";
    private String urlPrefix       = "";
    private String urlSuffix       = "";

    public DataBaseProxy() {
    }

    @Override
    public String getServerName() {
        return this.serverName;
    }

    @Override
    public void setServerName(String name) {
        this.serverName = name;
    }

    @Override
    public int getServerPort() {
        return this.serverPort;
    }

    @Override
    public void setServerPort(int port) {
        this.serverPort = port;
    }

    @Override
    public String getDatabaseName() {
        return this.databaseName;
    }

    @Override
    public void setDatabaseName(String name) {
        this.databaseName = name;
    }

    @Override
    public Statement getStatement() {
        return this.statement;
    }

    public void setDatabaseType(String driverClassName, String urlPrefix) {
        this.driverClassName = driverClassName;
        this.urlPrefix = urlPrefix;
    }

    public void setDatabaseType(String driverClassName, String urlPrefix, String urlSuffix) {
        this.driverClassName = driverClassName;
        this.urlPrefix = urlPrefix;
        this.urlSuffix = urlSuffix;
    }

    @Override
    public boolean connect(String username, String password) throws DriverNotFoundException {
        assert !this.driverClassName.equals("") : "You need set database type first.";

        this.username = username;
        this.password = password;

        try {
            Class.forName(this.driverClassName);
        }
        catch (ClassNotFoundException exception) {
            throw new DriverNotFoundException();
        }

        try {
            this.connection = DriverManager.getConnection(this.getUrl(), username, password);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }

        if (this.connection == null) {
            return false;
        }

        try {
            this.statement = this.connection.createStatement();
        }
        catch (SQLException exception) {
            this.disconnect();
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean disconnect() {
        if (this.connection == null) {
            return true;
        }

        try {
            this.connection.close();
        }
        catch (SQLException exception) {
            return false;
        }

        return true;
    }

    public boolean reconnect() throws DriverNotFoundException {
        this.disconnect();
        return this.connect(this.username, this.password);
    }

    public Properties getConfig() {
        return new Properties();
    }

    protected String getUrl() {
        assert !this.urlPrefix.equals("") : "You need set database type first.";
        assert !this.databaseName.equals("") : "You need set database name first.";

        StringBuilder url = new StringBuilder();
        url.append(this.urlPrefix);

        if (!this.serverName.equals("")) {
            url.append(this.serverName);

            if (this.serverPort != 0) {
                url.append(":");
                url.append(this.serverPort);
            }

            url.append("/");
        }

        url.append(this.databaseName);

        if (!this.urlSuffix.equals("")) {
            url.append("?");
            url.append(this.urlSuffix);
        }

        return url.toString();
    }

    public Properties getHibernateConfig() {
        Properties props = new Properties();

        RunArguments args = RunArguments.getInstance();

        if (args.isDebugMode()) {
            props.setProperty("hibernate.show_sql", "true");
            props.setProperty("hibernate.format_sql", "true");
        } else {
            props.setProperty("hibernate.show_sql", "false");
            props.setProperty("hibernate.format_sql", "false");
        }

        props.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        props.setProperty("hibernate.hbm2ddl.auto", "update");

        props.setProperty("hibernate.connection.driver_class", this.driverClassName);
        props.setProperty("hibernate.connection.url", this.getUrl());
        props.setProperty("hibernate.connection.username", this.username);
        props.setProperty("hibernate.connection.password", this.password);

        return props;
    }
}
