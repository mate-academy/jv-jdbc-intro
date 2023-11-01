package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                           PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Can not to create a book", e);
        }
    }

    private Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = createBookFromResultSet(resultSet);
                    return Optional.of(book);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not to get list of books by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Book book = createBookFromResultSet(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not to get list of books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int update = statement.executeUpdate();

            if (update == 0) {
                throw new RuntimeException(
                        "Can not to update a book. Book by id = " + book.getId() + " not found."
                );
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Can not to update a book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new RuntimeException(
                        "Can not deleted book. The book by id = " + id + " not found."
                );
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Can not deleted book", e);
        }
    }
}
