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
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException(
                        "expected to insert at least one row, but instead 0 rows"
                );
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to a DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Book> list = mappingResSet(resultSet);

            if (!list.isEmpty()) {
                return Optional.of(list.get(0));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to a DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";

        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            return mappingResSet(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to a DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET price = ? WHERE title = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, book.getPrice());
            statement.setString(2, book.getTitle());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException(
                        "expected to update at least one row, but instead 0 rows"
                );
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to a DB", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to a DB", e);
        }
    }

    private List<Book> mappingResSet(ResultSet resultSet) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");

            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
            books.add(book);
        }
        return books;
    }
}
