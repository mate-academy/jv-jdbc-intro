package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionUtil {
	private static final String DB_URL;
	private static final Properties DB_PROPERTIES;

	static {
		DB_PROPERTIES = new Properties();
		DB_PROPERTIES.put("user", "root");
		DB_PROPERTIES.put("password", "apaKanamalaka4a@");

		DB_URL = "jdbc:mysql://localhost:3306/cars";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
