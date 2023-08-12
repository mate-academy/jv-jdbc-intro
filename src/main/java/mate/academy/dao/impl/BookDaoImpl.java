package mate.academy.dao.impl;

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
    private static final int ID_INDEX = 1;

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO books (title,price) VALUES (?,?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject(ID_INDEX, Long.class));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create book " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM books WHERE id=?  AND is_deleted=FALSE";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book = parseBook(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book by id: " + id, e);
        }
    }

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setPrice(resultSet.getBigDecimal("price"));
        book.setTitle(resultSet.getString("title"));
        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM books WHERE is_deleted=FALSE";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookList.add(parseBook(resultSet));
            }
        } catch (
                  SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books "
                       + "SET title = ?, price = ? "
                       + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update book: "
                                              + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE books SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete book with id " + id, e);
        }
    }
}
