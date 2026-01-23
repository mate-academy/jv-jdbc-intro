package mate.academy.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    public static final String CREATE = "INSERT INTO books(title, price) VALUES(?, ?)";
    public static final String FIND_BY_ID = "SELECT * FROM books WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM books";
    public static final String UPDATE = "UPDATE books SET title = ?, price = ? "
            + "WHERE id = ?";
    public static final String DELETE_BY_ID = "DELETE FROM books WHERE id = ?";
    public static final int COLUMN_ONE = 1;
    public static final int COLUMN_TWO = 2;
    public static final int COLUMN_THREE = 3;

    @Override
    public Book create(Book book) {

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(COLUMN_ONE, book.getTitle());
            statement.setBigDecimal(COLUMN_TWO, book.getPrice());
            int rows = statement.executeUpdate();
            if (rows < 1) {
                throw new DataProcessingException("At least one row was expected to "
                        + "be inserted, but 0 rows were inserted.");
            }
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getObject(COLUMN_ONE, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a book" + book, e);
        }
        return book;

    }

    @Override
    public Optional<Book> findById(Long id) {

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(COLUMN_ONE, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = parseBook(resultSet);
                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = parseBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(COLUMN_ONE, book.getTitle());
            statement.setBigDecimal(COLUMN_TWO, book.getPrice());
            statement.setLong(COLUMN_THREE, book.getId());
            int rows = statement.executeUpdate();
            if (rows < 1) {
                throw new DataProcessingException("At least one row was expected to "
                        + "be inserted, but 0 rows were inserted.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't change a book" + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(COLUMN_ONE, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id" + id, e);
        }
    }

    private Book parseBook(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getObject("price", BigDecimal.class);
            return new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set parameters book for method", e);
        }
    }
}
