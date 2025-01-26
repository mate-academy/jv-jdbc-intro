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
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;

public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row"
                        + " but inserted 0 rows");
            }

            ResultSet id = preparedStatement.getGeneratedKeys();
            if (id.next()) {
                Long rowId = id.getObject(1, Long.class);
                book.setId(rowId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute create operation", e);
        }
        return book;
    }

    @Override
    public Optional<Book> getById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long rowId = resultSet.getObject("id", Long.class);
                String rowTitle = resultSet.getString("title");
                BigDecimal rowPrice = resultSet.getObject("price", BigDecimal.class);
                Book book = new Book();
                book.setId(rowId);
                book.setTitle(rowTitle);
                book.setPrice(rowPrice);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute getById operation", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long rowId = resultSet.getObject("id", Long.class);
                String titleRow = resultSet.getString("title");
                BigDecimal priceRow = resultSet.getBigDecimal("price");
                Book book = new Book();
                book.setId(rowId);
                book.setTitle(titleRow);
                book.setPrice(priceRow);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute findAll operation", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row"
                        + " but inserted 0 rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute update operation", e);
        }

        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected < 1) {
                throw new RuntimeException("Expected to delete at least one row"
                        + " but deleted 0 rows");
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't execute delete operation", e);
        }
    }
}
