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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        if (book == null) {
            throw new DataProcessingException("Book is null.", new RuntimeException());
        }

        if (book.getTitle() == null) {
            throw new DataProcessingException("Book Title is null", new RuntimeException());
        }

        if (book.getTitle().isEmpty()) {
            throw new DataProcessingException("Book Title is empty", new RuntimeException());
        }

        if (book.getPrice() == null) {
            throw new DataProcessingException("Book Price is null", new RuntimeException());
        }

        if (book.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new DataProcessingException("Book Price is null", new RuntimeException());
        }

        String sql = "INSERT INTO books (title,price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least 1 row, "
                        + "but actually got 0 rows were inserted.", new RuntimeException());
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not add book to database: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                Book foundedBook = new Book();
                foundedBook.setId(id);
                foundedBook.setTitle(title);
                foundedBook.setPrice(price);
                return Optional.of(foundedBook);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book by ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT id, title, price FROM books";

        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));

                books.add(book);
            }

            return books;

        } catch (SQLException e) {
            throw new DataProcessingException("Can not find books", e);
        }
    }

    @Override
    public Book update(Book book) {
        Book foundedBook = findById(book.getId()).orElseThrow();
        foundedBook.setTitle(book.getTitle());
        foundedBook.setPrice(book.getPrice());
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least 1 row, "
                        + "but actually got 0 rows were inserted.", new RuntimeException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update book: " + book, e);
        }
        return foundedBook;

    }

    @Override
    public boolean deleteById(Long id) {
        Book foundedBook = findById(id).orElseThrow();
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book by ID: " + id, e);
        }
    }
}
