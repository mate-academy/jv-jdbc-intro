package mate.academy.dao.bookdao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.bookdao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_QUERY = "INSERT INTO book (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM book where id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM book";
    private static final String UPDATE_QUERY = "UPDATE book SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM book WHERE id = ?";
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String PRICE_COLUMN = "price";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            checkUpdatedRows(statement.executeUpdate());
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                book.setId(id);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException(String.format(
                    "Can`t create a book %s in the database.", book), ex);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException(String.format(
                    "Can`t find book with id %d in the database", id), ex);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can`t find all books in the database.", ex);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            checkUpdatedRows(statement.executeUpdate());
        } catch (SQLException ex) {
            throw new DataProcessingException(String.format(
                    "Can`t update a book %s in the database", book), ex);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException(String.format(
                    "Can`t delete a book with id=%d from database", id), ex);
        }
    }

    private void checkUpdatedRows(int rows) {
        if (rows < 1) {
            throw new DataProcessingException("The book has not been added to the database");
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(ID_COLUMN));
        book.setTitle(resultSet.getString(TITLE_COLUMN));
        book.setPrice(resultSet.getBigDecimal(PRICE_COLUMN));
        return book;
    }
}
