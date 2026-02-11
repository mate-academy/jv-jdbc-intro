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
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.lib.DataProcessingException;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int ID_COLUMN_INDEX = 1;
    private static final int TITLE_COLUMN_INDEX = 2;
    private static final int PRICE_COLUMN_INDEX = 3;

    @Override
    public Book create(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book can't be null.");
        }
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, "
                        + "but was: " + affectedRows);
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_COLUMN_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save the book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Book's id can't be null.");
        }
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = mapToBook(resultSet);
                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't find the book with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        final List<Book> resultList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = mapToBook(resultSet);
                resultList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't fetch all data from DB.", e);
        }
        return resultList;
    }

    @Override
    public Book update(Book book) {
        Book fetchedBook = findById(book.getId()).orElseThrow(() ->
                new RuntimeException("Can't update the book as it's not present."));
        if (fetchedBook.equals(book)) {
            return book;
        }
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to update at least one row, "
                        + "but was: " + affectedRows);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Book's id can't be null.");
        }
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book with id: " + id, e);
        }
    }

    private Book mapToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID_COLUMN_INDEX, Long.class);
        String title = resultSet.getObject(TITLE_COLUMN_INDEX, String.class);
        BigDecimal price = resultSet.getObject(PRICE_COLUMN_INDEX, BigDecimal.class);

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);

        return book;
    }
}
