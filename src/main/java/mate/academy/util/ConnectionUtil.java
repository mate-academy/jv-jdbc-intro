package mate.academy.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {
    public static final String URL
            = "jdbc:mysql://localhost:3306/jv_jdbc_base?serverTimezone=UTC";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "Qq123456";
    private static final String INIT_DB_FILE
            = "src/main/resources/init_db.sql";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            executeInitScript();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver ", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to database ", e);
        }
    }

    private static void executeInitScript() {
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                BufferedReader reader = new BufferedReader(new FileReader(INIT_DB_FILE))) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
            statement.execute(sql.toString());

        } catch (IOException | SQLException e) {
            throw new RuntimeException("Error executing init_db.sql", e);
        }
    }
}
