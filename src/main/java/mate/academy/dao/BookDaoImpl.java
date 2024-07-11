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
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.models.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sqlCommand = "INSERT INTO books (title,price) VALUE (?,?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("None of the rows have been added");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getObject(1, Long.class);
                    book.setId(id);
                    return book;
                } else {
                    throw new DataProcessingException("Failed to retrieve the generated key");
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a book", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlCommand = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getBookFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sqlCommand = "SELECT * FROM books";
        List<Book> result = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                result.add(getBookFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }

        return result;
    }

    @Override
    public Book update(Book book) {
        String sqlCommand = "UPDATE books SET title=?, price=? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("None of the rows have been updated");
            }

            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlCommand = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {

            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id", e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getObject("title", String.class);
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);

        return new Book(id, title, price);
    }
}
