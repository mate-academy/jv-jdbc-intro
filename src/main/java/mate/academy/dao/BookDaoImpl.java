package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long rowId = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);

        Book book = new Book();
        book.setId(rowId);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }

    @Override
    public Book create(Book book) {

        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Create Book failed", null);
            }

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();

            if (generatedKey.next()) {
                book.setId(generatedKey.getObject(1, Long.class));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(extractBookFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with ID = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(extractBookFromResultSet(resultSet));
            }
            return books;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create list of books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Update Book failed", null);
            }
            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books where id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Delete book failed", null);
            }
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with ID = " + id, e);
        }
    }
}
