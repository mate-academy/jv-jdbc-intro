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

@Dao
public class BookDaoImpl implements BookDao {
    private static final int ID_INDEX = 1;
    private static final int ID_POSITION = 3;
    private static final int PRICE_POSITION = 2;
    private static final int TITLE_POSITION = 1;

    @Override
    public Book create(Book book) {
        String insert = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(insert,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_POSITION, book.getTitle());
            statement.setBigDecimal(PRICE_POSITION, book.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(ID_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String selectById = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionDB.getConnection();
                 PreparedStatement statement = connection.prepareStatement(selectById)) {
            statement.setLong(ID_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String selectAll = "SELECT * FROM books";
        try (Connection connection = ConnectionDB.getConnection();
                 PreparedStatement statement = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(createBook(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String update = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionDB.getConnection();
                 PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(TITLE_POSITION, book.getTitle());
            statement.setObject(PRICE_POSITION, book.getPrice());
            statement.setLong(ID_POSITION, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteById = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionDB.getConnection();
                 PreparedStatement statement = connection.prepareStatement(deleteById)) {
            statement.setLong(ID_INDEX, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private Book createBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
