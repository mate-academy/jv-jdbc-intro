package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.services.Book;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class BookDaoImpl implements BookDao {
    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO books (model, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getModel());
            statement.setObject(2, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at leas one row, but inserted 0 rows.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Book get(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String model = resultSet.getString("model");
                Integer price = resultSet.getObject("price", Integer.class);

                Book book = new Book();
                book.setId(id);
                book.setModel(model);
                book.setPrice(price);

                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not create a connection to the DB", e);
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Book update(Book book) {
        return null;
    }
}
