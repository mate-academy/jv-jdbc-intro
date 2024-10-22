package mate.academy.book;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.connection.ConnectionUtils;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        int titlePlace = 1;
        int pricePlace = 2;
        String sqlInsertionStatement = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlInsertionStatement,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(titlePlace, book.getTitle());
            statement.setBigDecimal(pricePlace, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        book.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to insert book: " + book.toString(), e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        int variablePlace = 1;
        String sqlGetStatement = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlGetStatement)) {
            statement.setLong(variablePlace,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long idFromDB = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Book book = new Book(idFromDB,title,price);
                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Cant get book with id:" + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sqlFindAllStatement = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlFindAllStatement);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book(id, title, price);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read data", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        int idPlace = 3;
        int titlePlace = 1;
        int pricePlace = 2;
        String sqlUpdateStatement = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlUpdateStatement)) {
            statement.setLong(idPlace,book.getId());
            statement.setString(titlePlace,book.getTitle());
            statement.setBigDecimal(pricePlace,book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return book;
            } else {
                throw new RuntimeException("Failed to update book with id: " + book.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update " + book.toString(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        int idPlace = 1;
        String sqlDeleteStatement = "DELETE FROM book WHERE id = ? ";
        try (Connection connection = ConnectionUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlDeleteStatement)) {
            statement.setLong(idPlace,id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }
}
