package mate.academy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import mate.academy.connection.ConnectionUtil;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        initializeDatabase();
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book(1L, "First blood", new BigDecimal("950.50"));
        Book book2 = new Book(2L, "Heroes above us", new BigDecimal("350.20"));

        performDatabaseOperations(bookDao, book1, book2);
    }

    private static void initializeDatabase() {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            String sql = readSqlFromFile("src/main/resources/init_db.sql");
            statement.execute("DROP TABLE IF EXISTS books");
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DataProcessingException("Error initializing the database", e);
        }
    }

    private static void performDatabaseOperations(BookDao bookDao, Book book1, Book book2) {
        try {
            // Create books
            bookDao.create(book1);
            bookDao.create(book2);

            // Find by ID
            System.out.println("Book found by ID: " + bookDao.findById(book1.getId()));

            // Update book
            book1.setTitle("Second blood");
            bookDao.update(book1);
            System.out.println("Book after update: " + bookDao.findById(book1.getId()));

            // Find all books
            System.out.println("All books: " + bookDao.findAll());

            // Delete book
            bookDao.deleteById(book2.getId());
            System.out.println("All books after deletion: " + bookDao.findAll());
        } catch (DataProcessingException e) {
            System.err.println("Database operation error: " + e.getMessage());
        }
    }

    private static String readSqlFromFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new DataProcessingException("Could not read from SQL file", e);
        }
        return sb.toString();
    }
}
