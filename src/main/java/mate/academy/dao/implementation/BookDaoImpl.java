package mate.academy.dao.implementation;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.service.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.model.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) Values (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                         PreparedStatement statement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row,"
                        + "but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price",
                        BigDecimal.class);

                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not create a connection to the DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book newBook = createBook(resultSet);
                books.add(newBook);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update the book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete the book with id: " + id, e);
        }
    }

    private Book createBook(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getObject("title", String.class);
            BigDecimal price = resultSet.getObject("price", BigDecimal.class);
            return new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data from the resultSet - "
                    + resultSet, e);
        }
    }
}
