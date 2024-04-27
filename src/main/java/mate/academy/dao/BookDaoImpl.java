package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.util.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book save(Book book) {
        String createQuery = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(createQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows <= 0) {
                throw new RuntimeException("Expected to insert 1 row or more");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not save book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book;
        String selectQuery = "SELECT * FROM book WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = parseBook(resultSet);
            } else {
                throw new DataProcessingException("Expected not empty result.");
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get book by id ." + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String selectAllQuery = "SELECT * FROM book";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            while (resultSet.next()) {
                bookList.add(parseBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all books", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String createQuery = "UPDATE book SET title=?, price=? WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(createQuery)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new DataProcessingException("Expected to update 1 row exactly");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery = "DELETE FROM book WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new DataProcessingException("Expected to delete 1 row exactly");
            }
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book by id " + id, e);
        }
    }

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(1));
        book.setTitle(resultSet.getString(2));
        book.setPrice(resultSet.getBigDecimal(3));
        return book;
    }
}
