package cap;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

import cap.db.DataBaseProxyHSQL;
import cap.db.DataBaseProxyMySQL;
import cap.db.DataBaseProxySQLite;
import cap.db.IDataBaseProxy;
import cap.img.CaptchaImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class RunArguments {
    private static RunArguments instance = new RunArguments();

    private boolean debugGui   = false;
    private boolean debugMode  = false;
    private boolean serverMode = false;

    private int serverPort = 80;
    private int maxServerConnections = 30;
    private long maxImageSize = 524288; // 512 kByte
    private long maxRequestSize = 3145728; // 3 MByte

    private IDataBaseProxy databaseProxy = null;
    private String databaseUser = "";
    private String databasePassword = "";

    private ICaptchaSystem captchaSystem = null;
    private LinkedList<CaptchaImage> images = null;
    private URL findOn = null;

    public static RunArguments getInstance() {
        return instance;
    }

    public ICaptchaSystem getCaptchaSystem() {
        return this.captchaSystem;
    }

    public void setCaptchaSystem(final ICaptchaSystem captchaSystem) {
        this.captchaSystem = captchaSystem;
    }

    public boolean isDebugGui() {
        return this.debugGui;
    }

    public void setDebugGui(final boolean debugGui) {
        this.debugGui = debugGui;
    }

    public boolean isDebugMode() {
        return this.debugMode;
    }

    public void setDebugMode(final boolean debugMode) {
        this.debugMode = debugMode;
    }

    public Collection<CaptchaImage> getImages() {
        return this.images;
    }

    public void setImages(final LinkedList<CaptchaImage> images) {
        this.images = images;
    }

    public void setImages(final String[] imagePaths) {
        this.images = new LinkedList();

        for (String path : imagePaths) {
            this.images.add(new CaptchaImage(path));
        }
    }

    public boolean isServerMode() {
        return this.serverMode;
    }

    public void setServerMode(final boolean serverMode) {
        this.serverMode = serverMode;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(final int serverPort) {
        this.serverPort = serverPort;
    }

    public int getMaxServerConnections() {
        return this.maxServerConnections;
    }

    public void setMaxServerConnections(final int maxServerConnections) {
        this.maxServerConnections = maxServerConnections;
    }

    public long getMaxImageSize() {
        return this.maxImageSize;
    }

    public void setMaxImageSize(final long maxImageSize) {
        this.maxImageSize = maxImageSize;
    }

    public long getMaxRequestSize() {
        return this.maxRequestSize;
    }

    public void setMaxRequestSize(final long maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    public IDataBaseProxy getDatabaseProxy() {
        return this.databaseProxy;
    }

    public void setDatabaseName(final String databaseName) {
        this.databaseProxy.setDatabaseName(databaseName);
    }

    public void setDatabaseType(final String databaseType) throws CaptchalizeParseException {
        if (databaseType.equals("hsql")) {
            this.databaseProxy = new DataBaseProxyHSQL();
            this.databaseProxy.setDatabaseName("db/captchalize.db");
            this.databaseUser = "SA";

        } else if (databaseType.equals("sqlite")) {
            this.databaseProxy = new DataBaseProxySQLite();
            this.databaseProxy.setDatabaseName("db/captchalize.sqlitedb");

        } else if (databaseType.equals("mysql")) {
            this.databaseProxy = new DataBaseProxyMySQL();
            this.databaseProxy.setServerName("localhost");
            this.databaseProxy.setServerPort(3306);

        } else {
            throw new CaptchalizeParseException();
        }
    }

    public String getDatabasePassword() {
        return this.databasePassword;
    }

    public void setDatabasePassword(final String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseUser() {
        return this.databaseUser;
    }

    public void setDatabaseUser(final String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public void setDatabaseServerName(final String databaseServerName) {
        this.databaseProxy.setServerName(databaseServerName);
    }

    public void setDatabaseServerPort(final int serverPort) {
        this.databaseProxy.setServerPort(serverPort);
    }

    public URL getFindOn() {
        return this.findOn;
    }

    public void setFindOn(final URL findOn) {
        this.findOn = findOn;
    }

    private RunArguments() {
        try {
            this.setDatabaseType("hsql");
        } catch (CaptchalizeParseException exception) {
            if (this.debugMode) {
                exception.printStackTrace();
            }
        }
    }
}
