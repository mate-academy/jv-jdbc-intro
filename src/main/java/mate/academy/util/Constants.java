package mate.academy.util;

public class Constants {
    // Application paths
    public static final String APP_PROPERTIES_FILE = "src/main/resources/app.properties";
    // DB connection constants
    public static final String DB_DRIVER_TAG = "db.driver";
    public static final String DB_URL_TAG = "db.connection.url";
    // Book dao sql query
    public static final String SAVE_BOOK_QUERY = "INSERT INTO books(title, price) VALUES(?, ?)";
    public static final String GET_BOOK_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    public static final String GET_ALL_BOOKS_QUERY = "SELECT * FROM books";
    public static final String UPDATE_BOOK_QUERY =
            "UPDATE books SET title = ?, price = ? WHERE id = ?";
}
