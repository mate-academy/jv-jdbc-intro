package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert rows, but inserted 0 rows");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Insertion from DB failed", e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book(
                        id,
                        resultSet.getString("title"),
                        resultSet.getObject("price", BigDecimal.class)
                );

                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Search for entity from DB failed", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book WHERE " +
                "title IS NOT NULL " +
                "AND price IS NOT NULL";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getObject("price", BigDecimal.class)
                ));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Full selection from DB failed", e);
        }

        return books;
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        String selectQuery = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateStatement = connection
                     .prepareStatement(updateQuery)) {
            updateStatement.setString(1, book.getTitle());
            updateStatement.setObject(2, book.getPrice());
            updateStatement.setLong(3, book.getId());
            int affectedRows = updateStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to update rows, but updated 0 rows");
            }

            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setLong(1, book.getId());
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                return new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getObject("price", BigDecimal.class)
                );
            } else {
                throw new DataProcessingException("Failed to fetch updated record from DB");
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Setting an entity failed in DB", e);
        }
    }


    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to delete rows, but deleted 0 rows");
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Deletion from DB failed", e);
        }
        return true;
    }
}
