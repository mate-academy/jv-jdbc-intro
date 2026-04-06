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
import mate.academy.ConnectionUtil;
import mate.academy.dao.BookDao;
import mate.academy.lib.Dao;
import mate.academy.models.Book;
import mate.academy.models.DataProcessingException;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int MIN_ROWS = 1;
    private static final int ZERO_ROWS = 1;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books(title, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int createdRows = statement.executeUpdate();
            if (createdRows < MIN_ROWS) {
                throw new RuntimeException("Expected to insert rows weren't inserted");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                book.setId(id);
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create book with this title: "
                    + book.getTitle(), e);
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(2);
                BigDecimal price = resultSet.getBigDecimal(3);
                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find by this id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String title = resultSet.getString(2);
                BigDecimal price = resultSet.getBigDecimal(3);

                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not execute findAll operation", e);
        }
        return books;
    }

    @Override
    public Book updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int updatedRow = statement.executeUpdate();
            if (updatedRow < MIN_ROWS) {
                throw new RuntimeException("Expected to update at least 1 row but updated 0 rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update information of this book: "
                    + book.getTitle() + book.getId() + book.getPrice(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int deletedRow = statement.executeUpdate();
            return deletedRow > ZERO_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not to delete book by id: " + id, e);
        }
    }
}
