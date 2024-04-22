package mate.academy.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private static final String BOOK_CREATOR = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String BY_ID_FINDER = "SELECT * FROM books WHERE id = ?";
    private static final String ALL_BOOKS_FINDER = "SELECT * FROM books";
    private static final String ROWS_UPDATER = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String BY_ID_DELETER = "DELETE FROM books WHERE id = ?";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final int TITLE_COLUMN = 1;
    private static final int PRICE_COLUMN = 2;
    private static final int ID_COLUMN = 3;
    private static final int ID_ORIGINAL_COLUMN = 1;

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(BOOK_CREATOR,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_COLUMN, book.getTitle());
            statement.setBigDecimal(PRICE_COLUMN, book.getPrice());
            int addedRows = statement.executeUpdate();
            if (addedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row, "
                        + "but 0 rows were inserted.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_ORIGINAL_COLUMN, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(BY_ID_FINDER)) {
            statement.setLong(ID_ORIGINAL_COLUMN, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = bookFromResultSet(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(ALL_BOOKS_FINDER)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(bookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find all books from table.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(ROWS_UPDATER)) {
            statement.setString(TITLE_COLUMN, book.getTitle());
            statement.setBigDecimal(PRICE_COLUMN, book.getPrice());
            statement.setLong(ID_COLUMN, book.getId());
            int addedRows = statement.executeUpdate();
            if (addedRows < 1) {
                throw new RuntimeException("Expected to update at least 1 row, "
                        + "but 0 rows were updated.");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update row using book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(BY_ID_DELETER)) {
            statement.setLong(ID_ORIGINAL_COLUMN, id);
            int deletedRow = statement.executeUpdate();
            return deletedRow > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book by id: " + id, e);
        }
    }

    private Book bookFromResultSet(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong(ID);
            String title = resultSet.getString(TITLE);
            BigDecimal price = resultSet.getBigDecimal(PRICE);
            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get data from ResultSet: " + resultSet, e);
        }
    }
}
