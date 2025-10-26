package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/book_store_db"
            + "?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123MySQL456;";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't find JDBC driver for MySQL", e);
        }
    }

    @Override
    public Book create(Book book) {
        String insertQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement createStatement = connection.prepareStatement(
                        insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, book.getTitle());
            createStatement.setBigDecimal(2, book.getPrice());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String selectQuery = "SELECT id, title, price FROM books WHERE id = ? AND deleted = FALSE";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement findStatement = connection.prepareStatement(selectQuery)) {
            findStatement.setLong(1, id);
            ResultSet resultSet = findStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractBookFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> allBooks = new ArrayList<>();
        String selectQuery = "SELECT id, title, price FROM books WHERE deleted = FALSE";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement findStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = findStatement.executeQuery();
            while (resultSet.next()) {
                allBooks.add(extractBookFromResultSet(resultSet));
            }
            return allBooks;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, book.getTitle());
            updateStatement.setBigDecimal(2, book.getPrice());
            updateStatement.setLong(3, book.getId());
            updateStatement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery = "UPDATE books SET deleted = TRUE WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
