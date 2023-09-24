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
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.model.DataProcessingException;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        Connection connection = ConnectionUtil.getConnection();
        String sqlInsert = "INSERT INTO books(title, price) VALUES (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(
                    sqlInsert,
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            if (affectedRows < 1) {
                throw new RuntimeException("Book wasn't added. 0 rows were affected");
            }
            statement.close();
            connection.close();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while adding book", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        Connection connection = ConnectionUtil.getConnection();
        String sqlSelect = "SELECT * FROM books WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlSelect);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.first()) {
                return Optional.empty();
            }
            statement.close();
            connection.close();
            return Optional.of(getBookFromResultSet(resultSet));
        } catch (SQLException e) {
            throw new DataProcessingException("Error occurred wile finding by id", e);
        }
    }

    @Override
    public List<Book> findAll() {
        Connection connection = ConnectionUtil.getConnection();
        String sqlSelect = "SELECT * FROM books";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlSelect);
            ResultSet resultSet = statement.executeQuery();
            List<Book> res = new ArrayList<>();
            while (resultSet.next()) {
                res.add(getBookFromResultSet(resultSet));
            }
            statement.close();
            connection.close();
            return res;
        } catch (SQLException e) {
            throw new DataProcessingException("Error occurred while getting all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        Connection connection = ConnectionUtil.getConnection();
        String sqlUpdate = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            if (affectedRows < 1) {
                throw new RuntimeException("Book wasn't changed. 0 rows were affected");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error occurred while updating book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = ConnectionUtil.getConnection();
        String sqlDelete = "DELETE FROM books WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlDelete);
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            statement.close();
            connection.close();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error occurred while deleting book", e);
        }
    }

    private static Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long bookId = resultSet.getLong("id");
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(bookId, title, price);
    }
}
