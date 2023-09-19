package mate.academy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books(title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getPrice().intValue());
            int executedRows = preparedStatement.executeUpdate();
            if (executedRows < 1) {
                throw new DataProcessingException("Expected to insert at least 1 row "
                        + "but inserted 0 rows");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(COLUMN_ID);
                    book.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add the new book", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    book = new Book(
                            id,
                            resultSet.getString(COLUMN_TITLE),
                            resultSet.getBigDecimal(COLUMN_PRICE));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find by id " + id, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books;";
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getLong(COLUMN_ID),
                        resultSet.getString(COLUMN_TITLE),
                        resultSet.getBigDecimal(COLUMN_PRICE));
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find any row", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        Book oldBook = findById(book.getId()).orElseThrow();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getPrice().intValue());
            preparedStatement.setLong(3, book.getId());
            int executedRows = preparedStatement.executeUpdate();
            if (executedRows < 1) {
                throw new DataProcessingException("Expected to update at least 1 row "
                        + "but updated 0 rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
        return oldBook;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, id);
            int executedRows = preparedStatement.executeUpdate();
            if (executedRows < 1) {
                return false;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete by id " + id, e);
        }
        return true;
    }
}
