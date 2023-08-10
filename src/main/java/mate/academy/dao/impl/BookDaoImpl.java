package mate.academy.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String INSERT_REQUEST = "INSERT INTO books(title, price) VALUES(?, ?)";
    private static final String REQUEST_BOOK_FROM_DB = "SELECT * FROM books WHERE id = ?";
    private static final String GET_ALL_REQUEST_BOOK = "SELECT * FROM books";
    private static final String UPDATE_REQUEST = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BOOK_REQUEST = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book data to DB. Book:" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement getStatement = connection.prepareStatement(REQUEST_BOOK_FROM_DB);
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            while (resultSet.next()) {
                book = getBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id:" + id, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement getAllStatement = connection.prepareStatement(GET_ALL_REQUEST_BOOK)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant get all books from DB.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement updateStatement = connection.prepareStatement(UPDATE_REQUEST)) {
            updateStatement.setLong(3, book.getId());
            updateStatement.setString(1, book.getTitle());
            updateStatement.setBigDecimal(2, book.getPrice());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book on DB. Book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement deleteStatement = connection.prepareStatement(DELETE_BOOK_REQUEST)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id " + id, e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        book.setId(resultSet.getObject("id", Long.class));
        return book;
    }
}
