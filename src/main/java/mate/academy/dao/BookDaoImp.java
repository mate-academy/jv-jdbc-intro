package mate.academy.dao;

import java.math.BigDecimal;
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
public class BookDaoImp implements BookDao {
    private static final String FIND_BY_ID = "SELECT * FROM Book WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM Book";
    private static final String DELETE_BY_ID = "DELETE FROM Book WHERE id = ?";
    private static final String ADD_TO_BOOK_BD = "INSERT INTO Book (title, price) VALUES (?, ?)";
    private static final String UPDATE_IN_BOOK_BD = "UPDATE Book SET Price = ? WHERE title = ?";

    @Override
    public Book create(Book book) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(ADD_TO_BOOK_BD, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Can't create new row "
                        + "affected rows was = " + affectedRows);
            }
            ResultSet resultKey = statement.getGeneratedKeys();
            if (resultKey.next()) {
                book.setId(resultKey.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new row", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                book = new Book();
                book.setPrice(price);
                book.setTitle(title);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find data by id", e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                Book book = new Book();
                book.setPrice(price);
                book.setTitle(title);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't findAll data", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(UPDATE_IN_BOOK_BD, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBigDecimal(1, book.getPrice());
            statement.setString(2, book.getTitle());
            statement.executeUpdate();
            ResultSet resultKey = statement.getGeneratedKeys();
            if (resultKey.next()) {
                book.setId(resultKey.getObject("id", Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update data", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                return false;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete data by id", e);
        }
        return true;
    }
}
