package mate.academy.dao;

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
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sqlQuery = "INSERT INTO books (title, price) values (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int result = statement.executeUpdate();
            if (result < 1) {
                throw new DataProcessingException("No rows were affected executing query. "
                        + statement
                        + "Something went wrong."
                        + "Unable to add book row:" + book, new SQLException());
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getLong(1));
            }
        } catch (DataProcessingException | SQLException de) {
            throw new DataProcessingException("Can't create new row in DB. "
                    + "Please check connection to database. "
                    + "So book ro wasn't created" + book, de);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong(1));
                book.setTitle(resultSet.getString(2));
                book.setPrice(resultSet.getBigDecimal(3));
                return Optional.of(book);
            } else {
                return Optional.empty();
            }
        } catch (DataProcessingException | SQLException de) {
            throw new DataProcessingException("Unable to get book by ID: "
                    + id + ". "
                    + "Please check the database connection.", de);
        }
    }

    @Override
    public List<Book> findAll() {
        String sqlQuery = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                books.add(book);
            }
        } catch (DataProcessingException | SQLException de) {
            throw new DataProcessingException("Unable to select books from table."
                    + "May be something is wrong to DB connection.", de);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sqlQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int result = statement.executeUpdate();
            if (result < 1) {
                throw new DataProcessingException("Unable to update book row"
                        + book, new SQLException());
            }
        } catch (DataProcessingException | SQLException de) {
            throw new DataProcessingException("Unable o update row for book"
                    + book, de);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            if (result < 1) {
                throw new DataProcessingException("Failed to update row with ID: " + id, new SQLException());
            } else {
                return true;
            }
        } catch (DataProcessingException | SQLException de) {
            throw new DataProcessingException("Can't delete the book with ID: "
                    + id
                    + " Please check the connection to database", de);
        }
    }
}
