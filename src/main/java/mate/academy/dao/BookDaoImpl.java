package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.model.Book;

public class BookDaoImpl implements BookDao {
    private static final String URL = "jdbc:mysql://localhost:3306/homework";
    private static final String USER = "root";
    private static final String PASSWORD = "vlad77kk";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?,?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Creating book failed, no ID obtained");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                } else {
                    throw new RuntimeException("Failed to create a book");
                }
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while creating a book", e);
        }

    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                books.add(mapBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error while finding all books", e);
        }
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapBookFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error while finding a book by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error while updating book", e);
        }
        throw new RuntimeException("Failed to update a book");
    }

    @Override
    public boolean delete(Book book) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, book.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while deleting a book", e);
        }
    }

    private Book mapBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        Book book = new Book();
        book.setId(id);
        book.setPrice(price);
        book.setTitle(title);
        return book;
    }
}
