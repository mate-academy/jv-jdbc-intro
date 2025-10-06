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
import mate.academy.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                "The book " + book + " does not update correctly.", null);
            }
            Optional<Book> updatedBook = findById(book.getId());
            if (updatedBook.isPresent()) {
                return updatedBook.get();
            }

        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update book with id " + book.getId(), e);
        }
        return book;
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id); {
                int affectedRows = preparedStatement.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id with id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> booksArrayList = new ArrayList<>();
        String countSql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(countSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = mapToBook(resultSet);
                booksArrayList.add(book);
            }
        } catch (DataProcessingException | SQLException e) {
            throw new DataProcessingException("Can't findAll books", e);
        }
        return booksArrayList;
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to insert at least one row, but inserted" + affectedRows, null);
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (DataProcessingException | SQLException e) {
            throw new DataProcessingException("Can't create book" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {

        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                Book book = mapToBook(resultSet);
                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id " + id, e);
        }
        return Optional.empty();
    }

    private static Book mapToBook(ResultSet resultSet) throws SQLException {
        Long rowId = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);

        Book book = new Book();
        book.setId(rowId);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
