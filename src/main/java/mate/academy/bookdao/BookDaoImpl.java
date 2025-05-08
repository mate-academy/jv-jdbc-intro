package mate.academy.bookdao;

import java.math.BigDecimal;
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
    public Book create(Book book) {
        String sql = "INSERT INTO books(title, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement preparedStatement = connection
                                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (storeBook(preparedStatement, book, false)) {
                throw new RuntimeException("Book could not be created");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement preparedStatement = connection
                                .prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                books.add(mapToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement preparedStatement = connection
                                .prepareStatement(sql)) {
            if (storeBook(preparedStatement, book, true)) {
                throw new RuntimeException("Book not found");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book.getTitle(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
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

    private Book mapToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return Book.of(id, title, price);
    }

    private boolean storeBook(PreparedStatement preparedStatement,
                              Book book, boolean idPresent) throws SQLException {
        if (idPresent) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            return preparedStatement.executeUpdate() < 1;
        }
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setBigDecimal(2, book.getPrice());
        return preparedStatement.executeUpdate() < 1;
    }
}
