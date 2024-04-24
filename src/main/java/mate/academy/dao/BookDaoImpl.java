package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exeptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    private static final int ID_COLUMN = 1;
    private static final int TITLE_COLUMN = 2;
    private static final int PRICE_COLUMN = 3;
    private static final int MIN_EXECUTE_UPDATE = 1;
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title,price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(TITLE_COLUMN, book.getTitle());
            statement.setBigDecimal(PRICE_COLUMN, book.getPrice());
            if (statement.executeUpdate() > MIN_EXECUTE_UPDATE) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    book.setId(resultSet.getLong(ID));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create book", e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(ID_COLUMN, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot book with id" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(getBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(ID_COLUMN, book.getId());
            statement.setString(TITLE_COLUMN, book.getTitle());
            statement.setBigDecimal(PRICE_COLUMN, book.getPrice());
            if (statement.executeUpdate() > MIN_EXECUTE_UPDATE) {
                return book;
            } else {
                throw new DataProcessingException("Cannot update book with id"
                        + book.getId(), null);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Expected to update at least 1 row, "
                    + "but 0 was updated." + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(ID_COLUMN, id);
            int executeUpdate = statement.executeUpdate();
            return executeUpdate > MIN_EXECUTE_UPDATE;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book with id" + id, e);
        }
    }

    public Book getBook(ResultSet resultSet) throws SQLException {
        String title = resultSet.getString(TITLE);
        BigDecimal price = resultSet.getBigDecimal(PRICE);
        Long id = resultSet.getObject(ID, Long.class);
        Book book = new Book(id, title, price);
        return book;
    }
}
