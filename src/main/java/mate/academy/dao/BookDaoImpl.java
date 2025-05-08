package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
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
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String createQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String findByIdQuery = "SELECT * FROM books WHERE id = ?";
    private static final String findAllQuery = "SELECT * FROM books";
    private static final String updateQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String deleteByIdQuery = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at less one, "
                        + "but inserted 0 rows", new SQLException());
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long bookId = generatedKeys.getObject(1, Long.class);
                book.setId(bookId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not add new book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }
        } catch (SQLException e) {
            String dbNameFromQuery = findAllQuery
                    .substring(findAllQuery.toLowerCase().indexOf("m") + 2);
            throw new DataProcessingException("Can not read all books from DB: "
                    + dbNameFromQuery, e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at less one, "
                        + "but inserted 0 rows", new SQLException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update data for book: title = "
                    + book.getTitle()
                    + "with price = "
                    + book.getPrice()
                    + ". For id = "
                    + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(deleteByIdQuery)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book from books DB for id = "
                    + id, e);
        }
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
