package mate.academy.dao;

import mate.academy.util.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books(title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can not add new book to the DB", ex);
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = getBook(resultSet);
                books.add(book);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can not get all fata from the 'books' table in DB",
                    ex);
        }
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = getBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get a book by id " + id, ex);
        }
        return Optional.empty();
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setObject(3, book.getId());
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update a book with id " + book.getId(), ex);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete a book with id " + id, ex);
        }
    }

    private static Book getBook(ResultSet resultSet) {
        Book book = new Book();
        Long id;
        String title;
        BigDecimal price;
        try {
            id = resultSet.getObject("id", Long.class);
            title = resultSet.getString("title");
            price = resultSet.getObject("price", BigDecimal.class);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't process results from SQL query!", e);
        }
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
