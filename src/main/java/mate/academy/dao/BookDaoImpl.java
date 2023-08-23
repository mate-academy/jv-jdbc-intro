package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public List<Book> getAll() {
        String query = "SELECT * FROM books WHERE is_deleted = FALSE";
        List<Book> allBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllBooks = connection.prepareStatement(query)) {
            ResultSet resultSet = getAllBooks.executeQuery(query);
            Book book = null;
            while (resultSet.next()) {
                book = createBookFromResultSet(resultSet);
                allBooks.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all books ", e);
        }
        return allBooks;
    }

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books(name,title) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createBookStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            createBookStatement.setString(1, book.getName());
            createBookStatement.setString(2, book.getTitle());
            createBookStatement.executeUpdate();
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create book " + book, e);
        }
        return book;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE books SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteBookStatement = connection.prepareStatement(query)) {
            deleteBookStatement.setLong(1, id);
            return deleteBookStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete book by id " + id, e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE library.books SET name = ?, title = ? "
                + " WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateBookStatement =
                        connection.prepareStatement(query)) {
            updateBookStatement.setString(1, book.getName());
            updateBookStatement.setString(2, book.getTitle());
            updateBookStatement.setLong(3, book.getId());
            updateBookStatement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update book to db"
                    + book, e);
        }
    }

    @Override
    public Optional<Book> get(Long id) {
        String query = "SELECT * FROM books WHERE id = ? AND"
                + " is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateBookStatement =
                        connection.prepareStatement(query)) {
            updateBookStatement.setLong(1, id);
            ResultSet resultSet = updateBookStatement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = createBookFromResultSet(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get book for id"
                    + id, e);
        }
    }

    private Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String title = resultSet.getString("title");
        Long id = resultSet.getObject("id", Long.class);
        return new Book(id, name, title);
    }
}
