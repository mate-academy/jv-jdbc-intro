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
import mate.academy.model.Book;

public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at less one row, "
                        + "but inserted 0 rows");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't add new book: " + book, e);
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
                Long idFromDb = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book();
                book.setId(idFromDb);
                book.setTitle(title);
                book.setPrice(price);

                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not find a book with id " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long idFromDb = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book();
                book.setId(idFromDb);
                book.setTitle(title);
                book.setPrice(price);

                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not find all the books from the DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int updatedRows = statement.executeUpdate();

            if (updatedRows < 1) {
                throw new RuntimeException("No book found with id " + book.getId());
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Can not update book with id " + book.getId());
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowDelete = statement.executeUpdate();
            return rowDelete > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can not delete book with id " + id, e);
        }
    }
}
