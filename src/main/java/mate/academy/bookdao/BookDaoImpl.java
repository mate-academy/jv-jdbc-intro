package mate.academy.bookdao;

import mate.academy.connection.ConnectionConfig;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int INDEX_ID = 1;
    private static final int TITLE_PART = 1;
    private static final int PRICE_PART = 2;
    private static final int ID_PART = 3;
    private static final String ADD_BOOK = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM books";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE id = ?";
    private static final String GET_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, price = ? WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ADD_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(TITLE_PART, book.getTitle());
            preparedStatement.setBigDecimal(PRICE_PART, book.getPrice());
            if (preparedStatement.executeUpdate() == 0) {
                throw new RuntimeException("Creating book failed, no rows affected.");
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(INDEX_ID, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("An error occurred while creating the book: "
                    + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BOOK_BY_ID)) {
            preparedStatement.setLong(INDEX_ID, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of( mapResultSetToBook(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error can't get book by ID", e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add( mapResultSetToBook(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK)) {
            preparedStatement.setString(TITLE_PART, book.getTitle());
            preparedStatement.setBigDecimal(PRICE_PART, book.getPrice());
            preparedStatement.setLong(ID_PART, book.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new RuntimeException("Updating book failed, no rows affected.");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while updating book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK)) {
            statement.setLong(INDEX_ID, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id = " + id, e);
        }
    }

    private Book  mapResultSetToBook(ResultSet resultSet) {
        try {
            return new Book(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    resultSet.getBigDecimal("price")
            );
        } catch (SQLException e) {
            throw new DataProcessingException("Error while mapping book", e);
        }
    }
}
