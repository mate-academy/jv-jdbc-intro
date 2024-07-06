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
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.utils.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String insertBookQuery = "INSERT INTO books (title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(insertBookQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Excepted to insert at least one row, "
                        + "but inserted 0 rows",
                        new RuntimeException());
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);

            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create  book with title: "
                    + book.getTitle()
                    + ", and price: "
                    + book.getPrice() + ":", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findBookQuery = "SELECT * from books WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(findBookQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = getBookFromResultSet(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book from id " + id + ":", e);
        }
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String getAllBookQuery = "SELECT * FROM books WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(getAllBookQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from DB:", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateBookQuery =
                "UPDATE books SET title = ?, price = ? WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(updateBookQuery)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Excepted to update at least one row, "
                        + "but updated 0 rows:",
                        new RuntimeException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book by id:" + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean delete(Long id) {
        String deleteBookQuery = "UPDATE books SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                            .prepareStatement(deleteBookQuery)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id:" + id + ":", e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
