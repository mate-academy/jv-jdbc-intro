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
    @Dao
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?,?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();

            try {
                boolean b = affectedRows < 1;
            } catch (DataProcessingException ex) {
                throw new DataProcessingException("Expected to insert at least one row, "
                        + "but inserted 0 rows.", ex);
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create new book: " + book, e);
        }
        return book;
    }

    @Dao
    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long rowId = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book();
                book.setId(rowId);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection", e);
        }
        return Optional.empty();
    }

    @Dao
    @Override
    public List<Book> findAll() {
        Long id = 0L;
        List<Book> bookList = new ArrayList<>();

        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet;

            do {
                resultSet = preparedStatement.executeQuery();
                Long rowId = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book();
                book.setId(rowId);
                book.setTitle(title);
                book.setPrice(price);

                bookList.add(book);
                id++;
            } while (resultSet.next());

        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection", e);
        }
        return bookList;
    }

    @Dao
    @Override
    public Book update(Book book) {
        String sql = "SELECT * FROM books WHERE title = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, book.getTitle());
            int affectedRows = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                book.setPrice(book.getPrice());
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create new book: " + book, e);
        }
    }

    @Dao
    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            try {
                boolean b = affectedRows < 1;
            } catch (DataProcessingException ex) {
                throw new DataProcessingException("Expected to delete at least one row, "
                        + "but deleted 0 rows.", ex);
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return affectedRows < 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete book with id = : " + id, e);
        }
        return false;
    }
}
