package mate.academy.bookdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dbconnection.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public int getRowsCount() {
        String sql = "SELECT COUNT(*) AS total_rows FROM book";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total_rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get row count", e);
        }
        return 0;
    }

    @Override
    public boolean clear() {
        String sql = "TRUNCATE TABLE book";
        try (Connection connection = ConnectionUtil.getConnection();
                        Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(sql);
            return affectedRows >= getRowsCount();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't clear table", e);
        }
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book(title, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection
                                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, book.getTitle());
            statement.setInt(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Book could not be created");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a new book " + book.getTitle(), e);
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
                String title = resultSet.getString("title");
                int price = resultSet.getInt("price");
                Book book = Book.of(id, title, price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                int price = resultSet.getInt("price");
                Book book = Book.of(id, title, price);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Book not found");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book.getTitle(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRow = statement.executeUpdate();
            if (affectedRow < 1) {
                throw new RuntimeException("Book not found by id " + id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by " + id, e);
        }
        return true;
    }
}
