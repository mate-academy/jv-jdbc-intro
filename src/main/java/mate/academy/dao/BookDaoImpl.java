package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import mate.academy.util.DataProcessingException;

@Dao
public class BookDaoImpl implements BookDao {
    public static final int TITLE_PARAMETER_INDEX = 1;
    public static final int PRICE_PARAMETER_INDEX = 2;
    public static final int ID_PARAMETER_INDEX = 3;
    public static final int ID_COLUMN_INDEX = 1;
    private static final String CREATE_QUERY =
            "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String GET_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY =
            "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_PARAMETER_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_PARAMETER_INDEX, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(ID_COLUMN_INDEX));
                return book;
            } else {
                throw new SQLException("Failed to create book, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error while creating book", e);
        }
    }

    @Override
    public Optional<Book> get(Long id) {
        try (Connection connection = new ConnectionUtil().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_QUERY)) {
            statement.setLong(ID_COLUMN_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setPrice(resultSet.getBigDecimal("price"));
                book.setTitle(resultSet.getString("title"));
                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Error while getting book by ID", e);
        }
    }

    @Override
    public List<Book> getAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                String bookTitle = resultSet.getString("title");
                BigDecimal bookPrice = resultSet.getBigDecimal("price");
                Long bookId = resultSet.getObject("id", Long.class);
                Book book = new Book();
                book.setId(bookId);
                book.setPrice(bookPrice);
                book.setTitle(bookTitle);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while getting all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(TITLE_PARAMETER_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_PARAMETER_INDEX, book.getPrice());
            statement.setLong(ID_PARAMETER_INDEX, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update book, no rows affected.");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while updating book", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(ID_COLUMN_INDEX, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while deleting book", e);
        }
    }
}
