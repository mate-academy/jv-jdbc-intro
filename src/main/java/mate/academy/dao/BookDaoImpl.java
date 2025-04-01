package mate.academy.dao;

import mate.academy.Book;
import mate.academy.ConnectionUtill;
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";

        try(Connection connection = ConnectionUtill.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (book.getTitle() == null || book.getPrice() == null) {
                throw new RuntimeException("Book title or price cannot be null");
            }

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("Failed to insert book " + book.getTitle());
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection", e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try(Connection connection = ConnectionUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();

                String title = resultSet.getObject("title", String.class);
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);

                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books";

        try(Connection connection = ConnectionUtill.getConnection();
            Statement statement = connection.createStatement();) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = new Book();

                String title = resultSet.getObject("title", String.class);
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                book.setId(resultSet.getLong("id"));
                book.setTitle(title);
                book.setPrice(price);

                books.add(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try(Connection connection = ConnectionUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            if (book.getTitle() == null || book.getPrice() == null || book.getId() == null) {
                throw new RuntimeException("Book id, title or price cannot be null");
            }

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Failed to insert book " + book.getTitle());
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection",e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try(Connection connection = ConnectionUtill.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("Book deletion failed " + id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection", e);
        }

        return true;
    }
}
