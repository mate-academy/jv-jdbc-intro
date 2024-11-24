package mate.academy.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import mate.academy.lib.Dao;
import mate.academy.lib.DataProcessingException;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    private Connection getConnection() {
        Properties properties = new Properties();
        try (FileInputStream fis
                     = new FileInputStream("src/main/resources/db.properties")) {
            properties.load(fis);
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            String driver = properties.getProperty("db.driver");
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (IOException e) {
            throw new DataProcessingException("Error loading properties file.", e);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Database driver not found.", e);
        } catch (SQLException e) {
            throw new DataProcessingException("Error connecting to the database.", e);
        }
    }

    @Override
    public Book save(Book book) {
        String query = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement
                = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error saving book to database.", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(long id) {
        String query = "SELECT * FROM book WHERE id = ?";
        Optional<Book> book = Optional.empty();
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Book findBook = new Book();
                    findBook.setId(resultSet.getLong("id"));
                    findBook.setTitle(resultSet.getString("title"));
                    findBook.setPrice(resultSet.getBigDecimal("price"));
                    book = Optional.of(findBook);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error getting book from database.", e);
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error getting books from database.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement statement
                = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("No book found with ID: " + book.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error updating book in database.", e);
        }
        return book;
    }

    @Override
    public void delete(Book book) {
        String query = "DELETE FROM book WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement statement
                = connection.prepareStatement(query)) {
            statement.setLong(1, book.getId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new RuntimeException("No book found with ID: " + book.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error deleting book from database.", e);
        }
    }
}
