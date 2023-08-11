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
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;
    private static final int THIRD_PARAMETER = 3;
    private static final int ID_COLUMN_INDEX = 1;
    private static final String ID_COLUMN_LABEL = "id";
    private static final String TITLE_COLUMN_LABEL = "title";
    private static final String PRICE_COLUMN_LABEL = "price";

    @Override
    public Book create(Book book) {
        String insertQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(FIRST_PARAMETER, book.getTitle());
            preparedStatement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("No inserted data! Something wrong with book: " + book);
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(ID_COLUMN_INDEX));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error during inserting book: " + book, e);
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        String selectAllQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.prepareStatement(selectAllQuery)) {
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            List<Book> acquiredBooks = new ArrayList<>();
            while (resultSet.next()) {
                Book nextBook = extractBookFromResultSet(resultSet);
                acquiredBooks.add(nextBook);
            }
            return acquiredBooks;
        } catch (SQLException e) {
            throw new DataProcessingException("Error during finding all books", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteRowByIdQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(deleteRowByIdQuery)) {
            preparedStatement.setLong(FIRST_PARAMETER,id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error deleting book by id: " + id, e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(FIRST_PARAMETER, book.getTitle());
            preparedStatement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            preparedStatement.setLong(THIRD_PARAMETER, book.getId());
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error during updating books database with book: "
                    + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findRowByIdQuery = "SELECT * FROM books WHERE id = ?";
        Optional<Book> value = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(findRowByIdQuery)) {
            preparedStatement.setLong(FIRST_PARAMETER, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book aquiredBook = extractBookFromResultSet(resultSet);
                value = Optional.of(aquiredBook);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error during finding book by id: " + id, e);
        }
        return value;
    }

    private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID_COLUMN_LABEL, Long.class);
        String title = resultSet.getString(TITLE_COLUMN_LABEL);
        BigDecimal price = resultSet.getObject(PRICE_COLUMN_LABEL, BigDecimal.class);
        return new Book(id, title, price);
    }
}
