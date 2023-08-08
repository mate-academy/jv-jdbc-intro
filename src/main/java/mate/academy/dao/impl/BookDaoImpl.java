package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String insertBookQuery = "INSERT INTO books(title, price) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createBookStatement =
                     connection.prepareStatement(
                             insertBookQuery, Statement.RETURN_GENERATED_KEYS)) {

            createBookStatement.setString(1, book.getTitle());
            createBookStatement.setObject(
                    2, book.getPrice());
            createBookStatement.executeUpdate();
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert book to DB" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findByIdQuery = "SELECT * FROM books "
                + "WHERE is_deleted = 'FALSE' AND id = ?;";
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement findByIdStatement =
                     connection.prepareStatement(findByIdQuery)) {

            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getObject("id", Long.class).equals(id)) {
                    book = getBookFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t find book by id from DB. id = " + id, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String getAllQuery = "SELECT * FROM books "
                + "WHERE is_deleted = 'FALSE';";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement findAllBooksStatement =
                     connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = findAllBooksStatement.executeQuery();
            while (resultSet.next()) {
                Book bookFromResultSet =
                        getBookFromResultSet(resultSet);
                books.add(bookFromResultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t find all books from DB" + books, e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateQuery =
                "UPDATE books SET title = ?, price = ? "
                        + "WHERE is_deleted = FALSE AND id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateBookStatement =
                     connection.prepareStatement(updateQuery)) {

            updateBookStatement.setString(1, book.getTitle());
            updateBookStatement.setObject(
                    2, book.getPrice());
            updateBookStatement.setLong(3, book.getId());
            updateBookStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery = "UPDATE books SET is_deleted = TRUE "
                + "WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteBookStatement =
                     connection.prepareStatement(deleteQuery)) {
            deleteBookStatement.setLong(1, id);
            return deleteBookStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t delete book by id from DB. id = " + id, e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
