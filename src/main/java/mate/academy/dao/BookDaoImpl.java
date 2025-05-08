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
import mate.academy.connection.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int COLUMN_TITLE = 1;
    private static final int COLUMN_PRICE = 2;
    private static final int COLUMN_ID = 3;
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(COLUMN_TITLE, book.getTitle());
            statement.setBigDecimal(COLUMN_PRICE, book.getPrice());

            int affectedRows = statement.executeUpdate();
            isUpdated(affectedRows, book);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(COLUMN_TITLE, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = new Book();
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(COLUMN_TITLE, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                mapToBook(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a book by id " + id, e);
        }
        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book = mapToBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't fetch books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(COLUMN_TITLE, book.getTitle());
            statement.setBigDecimal(COLUMN_PRICE, book.getPrice());
            statement.setLong(COLUMN_ID, book.getId());

            int affectedRows = statement.executeUpdate();
            isUpdated(affectedRows, book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        int affectedRows;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(COLUMN_TITLE, id);
            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete a book with id: " + id, e);
        }
        return affectedRows > 0;
    }

    private boolean isUpdated(int affectedRows, Book book) {
        if (affectedRows < 1) {
            throw new DataProcessingException(
                    "Expected to update at least one row, but updated nothing: "
                            + book);
        }
        return true;
    }

    private Book mapToBook(ResultSet resultSet) {
        Book book = new Book();
        try {
            long columnId = resultSet.getObject(ID, Long.class);
            Object columnPrice = resultSet.getObject(PRICE);
            String columnTitle = resultSet.getString(TITLE);
            book.setPrice((BigDecimal) columnPrice);
            book.setTitle(columnTitle);
            book.setId(columnId);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get result from DB");
        }
        return book;
    }
}
