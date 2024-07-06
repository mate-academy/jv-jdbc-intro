package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.models.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "henghfdf";

    @Override
    public Book create(Book book) {
        String sqlCommand = "INSERT INTO books (title,price) VALUE (?,?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("None of the rows have been added");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    return new Book(id, book.getTitle(), book.getPrice());
                } else {
                    throw new RuntimeException("Failed to retrieve the generated key");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create a connection to the DB", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlCommand = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getObject("title", String.class);
                    BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                    return Optional.of(new Book(id, title, price));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create a connection to the DB", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sqlCommand = "SELECT * FROM books";
        List<Book> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String title = resultSet.getObject("title", String.class);
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                result.add(new Book(id, title, price));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot create a connection to the DB", e);
        }

        return result;
    }

    @Override
    public Book update(Book book) {
        String sqlCommand = "UPDATE books SET title=?, price=? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("None of the rows have been updated");
            }

            return book;

        } catch (SQLException e) {
            throw new RuntimeException("Cannot create a connection to the DB", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlCommand = "DELETE FROM books WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {

            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Cannot create a connection to the DB", e);
        }
    }
}
