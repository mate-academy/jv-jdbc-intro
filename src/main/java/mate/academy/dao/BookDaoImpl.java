package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String READ_SCRIPT
            = "SELECT * FROM books WHERE id = ?";
    private static final String CREATE_SCRIPT
            = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String UPDATE_SCRIPT
            = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_SCRIPT
            = "DELETE FROM books WHERE id = ?";
    private static final String ALL_SCRIPT
            = "SELECT * FROM books";
    private static final String FIND_ID_SCRIPT
            = "SELECT id FROM books WHERE title = ?";

    @Override
    public Book create(Book book) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(CREATE_SCRIPT)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            book.setId(findID(book.getTitle()));
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a new book", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(READ_SCRIPT)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = newBook(resultSet.getObject("id", long.class),
                        resultSet.getString("title"),
                        resultSet.getObject("price", BigDecimal.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id", e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(ALL_SCRIPT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookList.add(newBook(resultSet.getObject("id", long.class),
                        resultSet.getString("title"),
                        resultSet.getObject("price", BigDecimal.class)));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find table", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(UPDATE_SCRIPT)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update data", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                    .prepareStatement(DELETE_SCRIPT)) {
            statement.setLong(1, id);
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book", e);
        }
    }

    private long findID(String title) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(FIND_ID_SCRIPT)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find max id", e);
        }
        return 0;
    }

    private Book newBook(long id, String title, BigDecimal price) {
        return new Book(id, title, price);
    }
}
