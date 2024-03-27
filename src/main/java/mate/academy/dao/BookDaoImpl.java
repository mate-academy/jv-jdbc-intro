package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_QUERY = "INSERT INTO books (id, title, price) VALUES (?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";
    private static final String URL = "jdbc:mysql://localhost:3306/book";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    @Override
    public Book create(Book book) {
        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setObject(1, book.getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setBigDecimal(3, book.getPrice());

            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows < 1) {
                throw new RuntimeException("Expected at least one row to insert");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book  " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Book(id, resultSet.getString("title"),
                        resultSet.getBigDecimal("price")));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            List<Book> books = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(resultSet.getLong("id"), resultSet.getString("title"), resultSet.getBigDecimal("price")));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows < 1) {
                throw new RuntimeException("Expected at least one row to insert");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, id);

            int deletedRows = preparedStatement.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }
}
