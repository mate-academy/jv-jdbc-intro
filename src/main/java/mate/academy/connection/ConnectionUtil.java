package mate.academy.connection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DВ_URL = "jdbc:mysql://localhost:3306/books_schema";
    private static final Properties DB_PROPERTIES;
    private static final String password;

    static {
        try {
            password = Files.readString(Path.of("src/password.txt"));
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load jdbc driver: ",e);
        } catch (IOException e) {
            throw new RuntimeException("Can`t find file with password: ", e);
        }
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user","root");
        DB_PROPERTIES.put("password",password);
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DВ_URL, DB_PROPERTIES);
    }
}
