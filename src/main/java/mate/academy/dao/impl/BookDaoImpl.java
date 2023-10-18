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
import mate.academy.util.ConnectionUtil;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affRows = statement.executeUpdate();
            if (affRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, "
                         + "but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert into table book. Book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        Book book = new Book();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long bookId = resultSet.getObject("id", Long.class);
                BigDecimal price = resultSet.getBigDecimal("price");
                String title = resultSet.getString("title");

                book.setId(bookId);
                book.setPrice(price);
                book.setTitle(title);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book from table book by id: " + id, e);
        }
        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                BigDecimal price = resultSet.getBigDecimal("price");
                String title = resultSet.getString("title");

                Book book = new Book();
                book.setId(id);
                book.setPrice(price);
                book.setTitle(title);

                books.add(book);
            }
            return books;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't read from table book", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affRows = preparedStatement.executeUpdate();
            if (affRows >= 1) {
                return book;
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update table book by Book: " + book, e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int affRows = preparedStatement.executeUpdate();

            return affRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book from table book by id: " + id, e);
        }
    }
}
