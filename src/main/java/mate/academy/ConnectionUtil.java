package mate.academy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import mate.academy.exceptions.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_INIT_PATH = "src/main/resources/init_db.sql";
    private static final String TABLE_BOOKS_NAME = "books";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "artem");
        DB_PROPERTIES.put("password", "Pssmgk_82");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            initializeTable();
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can not load JDBC driver");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }

    private static void initializeTable() {
        try (Connection connection = getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(
                    null,
                    null,
                    TABLE_BOOKS_NAME,
                    new String[]{"TABLE"}
            );
            boolean isTableExists = resultSet.next();
            if (!isTableExists) {
                executeSqlScript(connection, DB_INIT_PATH);
                System.out.println("Table initialized successfully");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not initialize the table. Error: " + e);
        }
    }

    private static void executeSqlScript(Connection connection, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
                Statement statement = connection.createStatement()) {
            String line;
            StringBuilder sql = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sql.append(line).append("\n");
            }
            statement.execute(sql.toString());
        } catch (SQLException | IOException e) {
            throw new DataProcessingException("Can not initialize the table. Error: " + e);
        }
    }
}
