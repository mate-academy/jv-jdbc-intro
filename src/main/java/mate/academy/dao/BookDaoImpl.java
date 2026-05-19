package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = ConnectionUtil
                .getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement
                    .executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Failed to create new book, "
                        + "no rows affected. Book: " + book);
            }

            try (ResultSet resultSet = preparedStatement
                    .getGeneratedKeys()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);

                    book.setId(id);

                    return book;
                }
            }

            throw new DataProcessingException("The book wasn't added. Book:" + book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new book. Book: "
                    + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT id, title, price FROM books WHERE id = ?";

        try (PreparedStatement preparedStatement = ConnectionUtil
                .getConnection()
                .prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = this.resultSetToBook(resultSet);

                    return Optional.of(book);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id. Id: "
                    + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";

        try (ResultSet resultSet = ConnectionUtil
                .getConnection()
                .prepareStatement(sql)
                .executeQuery()) {
            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                Book book = this.resultSetToBook(resultSet);

                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = ConnectionUtil
                .getConnection()
                .prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3,book.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Failed to update book, "
                        + "no rows affected. Book: " + book);
            }

            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book. Book: "
                    + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (PreparedStatement preparedStatement = ConnectionUtil
                .getConnection()
                .prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement
                    .executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id. Id: "
                    + id, e);
        }
    }

    private Book resultSetToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");

        return new Book(id, title, price);
    }
}
