package mate.academy.lib.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.DataProcessingException;
import mate.academy.lib.model.Book;

public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to insert at least row into book, but inserted 0 rows.");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book" + book,e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {

        String sql = "SELECT * FROM book WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long rowId = resultSet.getObject("id", Long.class);
                String rowTitle = resultSet.getString("title");
                BigDecimal rowPrice = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book();
                book.setId(rowId);
                book.setTitle(rowTitle);
                book.setPrice(rowPrice);

                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't creata a connection to the database");
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(new Book());
                books.get(books.size() - 1).setId(resultSet.getObject("id", Long.class));
                books.get(books.size() - 1).setTitle(resultSet.getString("title"));
                books.get(books.size() - 1).setPrice(resultSet
                        .getObject("price", BigDecimal.class));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't creata a connection to the database");
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Can't update book to " + book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book to " + book,e);
        }
        return book;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                return false;
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't creata a connection to the database");
        }
        return true;
    }
}
