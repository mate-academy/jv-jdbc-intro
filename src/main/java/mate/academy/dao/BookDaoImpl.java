package mate.academy.dao;

import mate.academy.connection.ConnectionUtil;
import mate.academy.exeption.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao{
    private ResultSet generatedKeys;

    @Override
    public Book create(Book book) {
        String createQuery = "INSERT INTO books(title,price) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(createQuery,
             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to add at least 1 book");
            }
            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1,Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create data for book" + book,e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findByIdQuery ="SELECT * FROM books WHERE id = (?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement
                     = connection.prepareStatement(findByIdQuery)) {
            statement.setLong(1, id);
            generatedKeys = statement.executeQuery();
            if (generatedKeys.next()) {
                Book book = getBookFromResultSet(generatedKeys);
                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find the book with id = " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String findAllQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(findAllQuery)) {
             generatedKeys = statement.executeQuery();
            while (generatedKeys.next()) {
                books.add(getBookFromResultSet(generatedKeys));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant create a connection to the DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update the book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete the book with id = " + id, e);
        }
    }

    private Book getBookFromResultSet(ResultSet generatedKeys) {
        try {
            Book book = new Book();
            book.setId(generatedKeys.getObject("id", Long.class));
            book.setTitle(generatedKeys.getString("title"));
            book.setPrice(generatedKeys.getBigDecimal("price"));
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not retrieve data from result set", e);
        }
    }
}
