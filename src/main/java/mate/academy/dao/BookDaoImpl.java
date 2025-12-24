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
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                             sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Cannot create a book "
                        + book, new RuntimeException("No rows affected"));
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create book " + book, e);
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
                Long rowId = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book();
                book.setId(rowId);
                book.setTitle(title);
                book.setPrice(price);

                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("cannot find a book ", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long rowId = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book();
                book.setId(rowId);
                book.setTitle(title);
                book.setPrice(price);

                books.add(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("cannot find a book ", e);
        }

        return books;
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == null) {
            throw new DataProcessingException("Id is needed for updating " + book,
                    new RuntimeException("Book id is null"));
        }

        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataProcessingException(
                        "Cannot update book, there is no id: " + book.getId(),
                        new RuntimeException("No rows affected"));
            }

            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book with id " + id, e);
        }

    }
}
