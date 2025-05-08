package mate.academy.lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not insert the book into the DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not find book from the DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books;";
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not find all from the DB", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("No rows were updated. The book with id "
                        + book.getId() + " might not exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not update data from DB", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can not delete data from DB", e);
        }
    }
}
