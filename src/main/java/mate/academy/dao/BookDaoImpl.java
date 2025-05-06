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
import mate.academy.exeption.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        try {
            connection = ConnectionUtil.getConnections();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Can not insert into books table");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to insert new book record", e);
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    throw new DataProcessingException("Failed to insert new book record", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DataProcessingException("Failed to insert new book record", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DataProcessingException("Failed to insert new book record", e);
                }
            }
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnections();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book = extractBookFromResultSet(resultSet);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not find Book with id - " + id + " ", e);
        }

        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection connection = ConnectionUtil.getConnections();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                books.add(extractBookFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not find Books ", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionUtil.getConnections();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update book, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book in the database: " + book, e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close Connection: " + e.getMessage());
                }
            }
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id=?";

        try (Connection connection = ConnectionUtil.getConnections();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException("Failed delete book with id "
                        + id + " from database");
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(("Failed delete book with id "
                    + id + " from database"));
        }
    }

    private Book extractBookFromResultSet(ResultSet resultSet) {
        Book book = null;
        try {
            book = new Book(resultSet.getString("title"),
                    resultSet.getObject("price", BigDecimal.class));
            book.setId(resultSet.getObject("id", Long.class));
        } catch (SQLException e) {
            throw new DataProcessingException("Can not extract book from database", e);
        }
        return book;
    }
}
