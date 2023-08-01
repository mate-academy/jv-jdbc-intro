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
        String createRequest = "INSERT INTO books "
                + "(title, price) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createManufacturerStatements =
                     connection.prepareStatement(createRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            createManufacturerStatements.setString(1, book.getTitle());
            createManufacturerStatements.setBigDecimal(2, book.getPrice());
            createManufacturerStatements.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatements.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String selectRequest = "SELECT title, price "
                + "FROM books "
                + "WHERE is_deleted = 0 AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getManufacturerStatements =
                     connection.prepareStatement(selectRequest)) {
            getManufacturerStatements.setLong(1, id);
            ResultSet resultSet = getManufacturerStatements.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                return Optional.of(getBook(id, title, price));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id: " + id, e);
        }
        return Optional.empty();
    }


    @Override
    public List<Book> findAll() {
        String selectRequest = "SELECT id, title, price "
                + "FROM books "
                + "WHERE is_deleted = 0;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getManufacturerStatements =
                     connection.prepareStatement(selectRequest)) {
            ResultSet resultSet = getManufacturerStatements.executeQuery();
            List<Book> bookList = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                bookList.add(getBook(id, title, price));
            }
            return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't select all books from DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateRequest = "UPDATE books "
                + "SET title = ?, price = ? "
                + "WHERE id = ? AND is_deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerStatements =
                     connection.prepareStatement(updateRequest)) {
            updateManufacturerStatements.setString(1, book.getTitle());
            updateManufacturerStatements.setBigDecimal(2, book.getPrice());
            updateManufacturerStatements.setLong(3, book.getId());
            int rows = updateManufacturerStatements.executeUpdate();
            if (rows != 1) {
                throw new DataProcessingException("Can't update book "
                        + book, new SQLException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book "
                    + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String updateRequest = "UPDATE books "
                + "SET is_deleted = 1 "
                + "WHERE id = ? AND is_deleted = 0";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateManufacturerStatements =
                     connection.prepareStatement(updateRequest)) {
            updateManufacturerStatements.setLong(1, id);
            int rows = updateManufacturerStatements.executeUpdate();
            if (rows == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with ID "
                    + id, e);
        }
        return false;
    }

    private Book getBook(Long id, String title, BigDecimal price) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return  book;
    }
}
