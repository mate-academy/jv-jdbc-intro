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
             PreparedStatement statement =
                     connection.prepareStatement(createRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
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
        String selectRequest = "SELECT id, title, price "
                + "FROM books "
                + "WHERE is_deleted = 0 AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(selectRequest)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                return Optional.of(getBook(resultSet));
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
             PreparedStatement statement =
                     connection.prepareStatement(selectRequest)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> bookList = new ArrayList<>();
            while (resultSet.next()) {
                bookList.add(getBook(resultSet));
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
             PreparedStatement statement =
                     connection.prepareStatement(updateRequest)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() != 1) {
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
             PreparedStatement statement =
                     connection.prepareStatement(updateRequest)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with ID "
                    + id, e);
        }
    }

    private Book getBook(ResultSet resultSet) {
        try {
            Book book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setTitle(resultSet.getString("title"));
            book.setPrice(resultSet.getBigDecimal("price"));
            return  book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get fields from result set", e);
        }
    }
}
