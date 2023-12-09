package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String username;
    private String host;
    private String password;
    private String port;
    private String dbName;

    static {
        //Loading JDBC driver;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load JDBC driver");
        }
    }

    public ConnectionUtil(String username, String host,
                          String password, String port, String dbName) {
        this.username = username;
        this.host = host;
        this.password = password;
        this.port = port;
        this.dbName = dbName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", username);
        dbProperties.put("password", password);
        try {
            return DriverManager.getConnection("jdbc:mysql://" + host + ":"
                    + port + "/" + dbName, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to data base");
        }
    }
}
