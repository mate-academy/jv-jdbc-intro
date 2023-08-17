package mate.academy.bookdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.connection.ConnectionConfig;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int INDEX_ID = 1;
    private static final int TITLE_PART = 1;
    private static final int PRICE_PART = 2;
    private static final int ID_PART = 3;

    @Override
    public Book create(Book book) {
        String addBookQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        addBookQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(TITLE_PART, book.getTitle());
            preparedStatement.setBigDecimal(PRICE_PART, book.getPrice());
            if (preparedStatement.executeUpdate() == 0) {
                throw new RuntimeException("Creating book failed, no rows affected.");
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(INDEX_ID, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book: "
                    + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String getBookByIdQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionConfig.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(getBookByIdQuery)) {
            preparedStatement.setLong(INDEX_ID, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToBook(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Error can't get book by ID: "
                    + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String selectAllBooksQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectAllBooksQuery)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(mapResultSetToBook(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateBookQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionConfig.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(updateBookQuery)) {
            preparedStatement.setString(TITLE_PART, book.getTitle());
            preparedStatement.setBigDecimal(PRICE_PART, book.getPrice());
            preparedStatement.setLong(ID_PART, book.getId());
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error while updating book: "
                    + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteBookQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteBookQuery)) {
            statement.setLong(INDEX_ID, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id = " + id, e);
        }
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        return new Book(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    resultSet.getBigDecimal("price")
            );
    }
}
