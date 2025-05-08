package mate.academy.dao;

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
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ? )";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            if (statement.executeUpdate() < 1) {
                throw new RuntimeException("Was inserted 0 row, not at least one row");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        Book foundBook = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundBook = getBookFromQuery(id, resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a book by id " + id, e);
        }
        return Optional.ofNullable(foundBook);
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookList.add(getBookFromQuery(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        int updatedRow;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            updatedRow = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book's information for id = "
                    + book.getId(), e);
        }
        return (updatedRow > 0) ? book : null;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        int deletedRow;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            deletedRow = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete a book by id = " + id, e);
        }
        return deletedRow > 0;
    }

    private Book getBookFromQuery(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getObject("id", Long.class),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price"));
    }

    private Book getBookFromQuery(Long id, ResultSet resultSet) throws SQLException {
        return new Book(id,
                resultSet.getString("title"),
                resultSet.getBigDecimal("price"));
    }

}
