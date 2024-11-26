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
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES(?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate < 1) {
                throw new RuntimeException("Cant add new book with following parameters: " + book);
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cant add new book with following parameters: " + book, e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                book = new Book();
                Long rowId = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                book.setId(rowId);
                book.setTitle(title);
                book.setPrice(price);
            }
        } catch (SQLException e) {
            throw new RuntimeException("findById failed", e);
        }

        Optional<Book> result = Optional.ofNullable(book);
        return result;
    }

    @Override
    public Book update(Book book) {
        if (book == null || book.getId() == null) {
            throw new RuntimeException("unacceptable data format");
        }
        Book currentBook = findById(book.getId()).orElseThrow(() -> new RuntimeException(
                "There is no object with such id: " + book.getId()));

        if (book.equals(currentBook)) {
            return book;
        }

        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("update failed", e);
        }

        currentBook = findById(book.getId()).get();
        if (currentBook.equals(book)) {
            return currentBook;
        }
        throw new RuntimeException("Update failed");
    }

    @Override
    public boolean delete(Long id) {
        if (findById(id).isEmpty()) {
            throw new RuntimeException(
                    "There is no object with such id: " + id);
        }

        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            if (findById(id).isPresent()) {
                return false;
            }

            return true;

        } catch (SQLException e) {
            throw new RuntimeException("delete failed", e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> result = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long rowId = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");

                Book book = new Book();
                book.setId(rowId);
                book.setTitle(title);
                book.setPrice(price);

                result.add(book);

            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("findAll failed", e);
        }
    }
}
