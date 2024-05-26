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
import mate.academy.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow < 0) {
                throw new SQLException("No rows affected");
            }

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                book.setId(getId(resultSet));
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book: " + book.toString(), e);
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Book(
                        getId(resultSet),
                        getTitle(resultSet),
                        getPrice(resultSet)
                ));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(getId(resultSet));
                book.setTitle(getTitle(resultSet));
                book.setPrice(getPrice(resultSet));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find any book", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow < 0) {
                throw new SQLException("No rows affected");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book.toString(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int affectedRow = preparedStatement.executeUpdate();

            return affectedRow > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private Long getId(ResultSet resultSet) throws SQLException {
        return resultSet.getObject(1, Long.class);
    }

    private BigDecimal getPrice(ResultSet resultSet) throws SQLException {
        return resultSet.getObject("price", BigDecimal.class);
    }

    private String getTitle(ResultSet resultSet) throws SQLException {
        return resultSet.getObject("title", String.class);
    }
}
