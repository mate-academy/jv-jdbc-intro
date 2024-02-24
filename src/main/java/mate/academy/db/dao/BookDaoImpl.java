package mate.academy.db.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.db.util.ConnectionUtil;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException();
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException | RuntimeException e) {
            throw new DataProcessingException("Can't add the book: " + book.getTitle(), e);
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
                return retrieveEntityIfExist(resultSet);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a book by ID: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> listOfBooks = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Optional<Book> retrievedBook = retrieveEntityIfExist(resultSet);
                listOfBooks.add(retrievedBook.get());
            }

            return listOfBooks;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all of the books.", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                return findById(book.getId()).get();
            }

            throw new RuntimeException();

        } catch (SQLException | RuntimeException e) {
            throw new DataProcessingException("Can't update the book with ID: " + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            int deletedRows = preparedStatement.executeUpdate();

            return deletedRows > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book with ID: " + id, e);
        }
    }

    private Optional<Book> retrieveEntityIfExist(ResultSet resultSet) throws SQLException {
        Long rowId = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);

        return Optional.of(new Book(rowId, title, price));
    }
}
