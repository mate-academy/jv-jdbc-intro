package mate.academy.dao;

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
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;
    private static final int THIRD_PARAMETER = 3;
    private static final int ID_INDEX = 1;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at leas one row, "
                        + "but inserted 0 rows.");
            }
            ResultSet keyResultSet = statement.getGeneratedKeys();
            if (keyResultSet.next()) {
                Long id = keyResultSet.getObject(ID_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(FIRST_PARAMETER, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id: " + id + "in DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM books")) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            if (resultSet.next()) {
                books.add(getBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find books in DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(FIRST_PARAMETER, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            statement.setLong(THIRD_PARAMETER, book.getId());
            int changes = statement.executeUpdate();
            if (changes < 1) {
                throw new RuntimeException("Expected to update at leas one row,"
                        + " but updated 0 rows.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book: " + book, e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(FIRST_PARAMETER, id);
            int changes = statement.executeUpdate();
            return changes > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id + " in DB", e);
        }
    }

    private Book getBook(ResultSet resultSet) {
        Book book = new Book();
        try {
            book.setId(resultSet.getLong("id"));
            book.setTitle(resultSet.getString("title"));
            book.setPrice(resultSet.getBigDecimal("price"));
        } catch (SQLException e) {
            throw new RuntimeException("Can't get book from ResultSet");
        }
        return book;
    }
}
