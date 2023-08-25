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
public class BookDaoIml implements BookDao {
    @Override
    public Book create(Book book) {
        String createQuery =
                "INSERT INTO book(title, price) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, book.getTitle());
            createStatement.setBigDecimal(2, book.getPrice());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create and add book to DB" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findQuery =
                "SELECT * FROM book WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                  PreparedStatement findStatement = connection.prepareStatement(findQuery)) {
            findStatement.setLong(1, id);
            ResultSet resultSet = findStatement.executeQuery();
            Book book = null;

            if (resultSet.next()) {
                book = getBookFromResultSet(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> booksList = new ArrayList<>();
        String query = "SELECT * FROM book WHERE is_deleted = FALSE;";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement findStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = findStatement.executeQuery();
            while (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet);
                booksList.add(book);
            }
            return booksList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateQuery =
                "UPDATE book SET title = ?, price = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, book.getTitle());
            updateStatement.setBigDecimal(2, book.getPrice());
            updateStatement.setLong(3, book.getId());
            updateStatement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book by id: " + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery =
                "UPDATE book SET is_deleted = TRUE WHERE id = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book from DB dy id: " + id, e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
