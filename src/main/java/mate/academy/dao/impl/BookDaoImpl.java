package mate.academy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.connection.ConnectionUtil;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.lib.IdInjector;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    IdInjector.injectIdFromDB(book, generatedKeys.getLong(1));
                } else {
                    throw new DataProcessingException("Creating book failed, no ID obtained.",
                            new RuntimeException());
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not insert new book: " + book + " to DB.", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = new Book();
                    book.setTitle(resultSet.getString("title"));
                    book.setPrice(resultSet.getBigDecimal("price"));
                    IdInjector.injectIdFromDB(book, resultSet.getLong("id"));
                    return Optional.of(book);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Book> books = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book();
                    book.setTitle(resultSet.getString("title"));
                    book.setPrice(resultSet.getBigDecimal("price"));
                    IdInjector.injectIdFromDB(book, resultSet.getLong("id"));
                    books.add(book);
                }
                return books;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int booksUpdated = preparedStatement.executeUpdate();
            if (booksUpdated == 0) {
                throw new DataProcessingException("Updating book failed, no book with ID '"
                        + book.getId() + "' obtained.", new RuntimeException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update book: " + book + " to DB.", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rowsRemoved = preparedStatement.executeUpdate();
            return rowsRemoved > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book with id: " + id, e);
        }
    }
}
