package mate.academy.dao;

import java.math.BigDecimal;
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
        String sqlCreateNewRow = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = ConnectionUtil
                .getConnection().prepareStatement(sqlCreateNewRow,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected ye insert at least 1 row, but was"
                        + affectedRows);
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book" + book.getTitle(), e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlFindRowById = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement statement = ConnectionUtil
                .getConnection().prepareStatement(sqlFindRowById)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String bookName = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book();
                book.setTitle(bookName);
                book.setPrice(price);
                book.setId(id);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sqlGetAllRows = "SELECT * FROM books";
        List<Book> bookList = new ArrayList<>();
        try (PreparedStatement statement = ConnectionUtil
                .getConnection().prepareStatement(sqlGetAllRows)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String bookName = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Long id = resultSet.getObject("id", Long.class);

                Book book = new Book();
                book.setTitle(bookName);
                book.setPrice(price);
                book.setId(id);
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get connection with this database", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String sqlUpdateRow = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = ConnectionUtil
                .getConnection().prepareStatement(sqlUpdateRow)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to update at least 1 row, but was: "
                        + affectedRows);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update this book value: "
                    + book.getTitle(), e);
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        String sqlDeleteRowById = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = ConnectionUtil
                .getConnection().prepareStatement(sqlDeleteRowById)) {
            statement.setLong(1, id);
            int resultSet = statement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with this id:" + id, e);
        }
    }
}
