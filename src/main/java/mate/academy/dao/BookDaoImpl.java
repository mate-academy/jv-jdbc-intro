package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(
                   query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException(
                       "Creating book failed, no rows affected.", new SQLException());
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                book.setId(id);
            } else {
                throw new DataProcessingException(
                       "Creating book failed, no ID obtained.", new SQLException());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create a book", e);
        } finally {
            closeResources(connection, preparedStatement, generatedKeys);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                         connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Book(
                           resultSet.getLong("id"),
                           resultSet.getString("title"),
                           resultSet.getBigDecimal("price")));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find a book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getBigDecimal("price")));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException(
                       "Updating book failed, no rows affected.", new SQLException());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update a book", e);
        } finally {
            closeResources(connection, preparedStatement);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException(
                       "Deleting book failed, no rows affected.", new SQLException());
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete a book with id: " + id, e);
        } finally {
            closeResources(connection, preparedStatement);
        }
    }

    private void closeResources(Connection connection, PreparedStatement preparedStatement) {
        closeResources(connection, preparedStatement, null);
    }

    private void closeResources(Connection connection,
                                PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DataProcessingException("Failed to close ResultSet", e);
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DataProcessingException("Failed to close PreparedStatement", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DataProcessingException("Failed to close Connection", e);
            }
        }
    }
}
