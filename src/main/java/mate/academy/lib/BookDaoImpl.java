package mate.academy.lib;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.Book;
import mate.academy.BookDao;
import mate.academy.util.ConnectionUtil;
import mate.academy.util.DataProcessingException;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String INSERT_BOOK_QUERY =
            "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SELECT_ALL_BOOKS_QUERY =
            "SELECT * FROM books";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(INSERT_BOOK_QUERY,
                                 PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setBigDecimal(2, book.getPrice());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    book.setId(resultSet.getLong(1));
                }
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error creating book", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next() ? Optional.of(mapBookFromResultSet(resultSet))
                            : Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<Book> books = new ArrayList<>();
                    while (resultSet.next()) {
                        books.add(mapBookFromResultSet(resultSet));
                    }
                    return books;
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error finding all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, book.getTitle());
                statement.setBigDecimal(2, book.getPrice());
                statement.setLong(3, book.getId());
                statement.executeUpdate();
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error updating book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error deleting book by id " + id, e);
        }
    }

    private Book mapBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");

        return new Book(id, title, price);
    }
}
