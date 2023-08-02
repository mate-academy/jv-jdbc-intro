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
    @Override
    public Book create(Book book) {
        String insertSqlRequest = "INSERT INTO books (title, price) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatement = connection
                     .prepareStatement(insertSqlRequest, Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatement.setString(1, book.getTitle());
            createManufacturerStatement.setBigDecimal(2, book.getPrice());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert book "
                    + book + " to DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String getBookRequest =
                "SELECT * FROM books WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getBookStatement = connection
                     .prepareStatement(getBookRequest)) {
            getBookStatement.setLong(1, id);
            ResultSet resultSet = getBookStatement.executeQuery();
            while (resultSet.next()) {
                Book book = createEntity(resultSet);
                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get book by id = " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String getAllRequest = "SELECT * FROM books WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllBooksStatement
                     = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = getAllBooksStatement.executeQuery(getAllRequest);
            while (resultSet.next()) {
                Book book = createEntity(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all books from DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateRequest = "UPDATE books SET title = ?, price = ? "
                + "WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateBookStatement =
                     connection.prepareStatement(updateRequest)) {
            updateBookStatement.setString(1, book.getTitle());
            updateBookStatement.setBigDecimal(2, book.getPrice());
            updateBookStatement.setString(3, String.valueOf(book.getId()));
            updateBookStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update book "
                    + book + " from DB", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteRequest = "UPDATE books SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteBookStatement =
                     connection.prepareStatement(deleteRequest)) {
            deleteBookStatement.setLong(1, id);
            return deleteBookStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete book by id "
                    + id + " from DB", e);
        }
    }

    private static Book createEntity(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        Book manufacturer = new Book();
        manufacturer.setId(id);
        manufacturer.setTitle(title);
        manufacturer.setPrice(price);
        return manufacturer;
    }
}
