package mate.academy.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String CREDENTIALS_PATH =
            "src/main/resources/credentials.env";
    public static Connection getConnection() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_PATH))) {
            String className = "com.mysql.cj.jdbc.Driver";
            Class.forName(className);

            String[] credentials = new String[2];
            credentials[0] = reader.readLine();
            credentials[1] = reader.readLine();

            Properties dbProperties = new Properties();
            dbProperties.put("user", credentials[0]);
            dbProperties.put("password", credentials[1]);


            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bookDB", dbProperties);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load JDBC driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create a connection to the DB", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException: " + e);
        }
    }
}
