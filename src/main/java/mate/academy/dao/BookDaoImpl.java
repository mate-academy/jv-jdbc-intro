package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO BOOKS (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setObject(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least one row,"
                        + " but inserted 0 rows.");
            }
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    System.out.println("Inserted " + affectedRows + " row(s).");
                    book.setId(resultSet.getObject(1, Long.class));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save book " + book, e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = new Book(resultSet.getObject("id", Long.class),
                            resultSet.getString("title"),
                            resultSet.getBigDecimal("price"));
                    preparedStatement.close();
                    resultSet.close();
                    return Optional.of(book);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    books.add(new Book(resultSet.getObject("id", Long.class),
                            resultSet.getString("title"),
                            resultSet.getBigDecimal("price")));
                }
            } else {
                return books;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE BOOKS SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setObject(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to update at least one row,"
                        + " but updated " + affectedRows + " rows.");
            }
            System.out.println("Updated " + affectedRows + " row(s).");
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM BOOKS WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Deleted " + affectedRows + " row(s).");
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book with id:" + id, e);
        }
    }
}
