package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.lib.DataProcessingException;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books(title, price) VALUES(?, ?)";
        Optional<Book> checkRecordInBase = findById(book.getId());
        if (checkRecordInBase.equals(Optional.empty())) {
            query = "INSERT INTO books(title, price, id) VALUES(?, ?, ?)";
        }

        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            if (checkRecordInBase.equals(Optional.empty())) {
                statement.setLong(3, book.getId());
            }

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Could not insert book into database. "
                        + "Book parameters: id=" + book.getId()
                        + ", title=" + book.getTitle()
                        + ", price=" + book.getPrice());
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }

            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(retrieveBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not retrieve book with id=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(retrieveBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not select books from database. "
                    + "Check table path or if the table exists.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Could not update the database for the book: id=" + book.getId()
                                + ", title=" + book.getTitle()
                                + ", price=" + book.getPrice());
            }

            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();
            return (rowsAffected > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Book retrieveBookFromResultSet(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");

            return new Book(id, title, price);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
