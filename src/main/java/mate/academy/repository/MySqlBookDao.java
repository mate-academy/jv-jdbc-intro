package mate.academy.repository;

import java.math.BigDecimal;
import java.sql.Connection;
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

@Dao
public class MySqlBookDao implements BookDao {
    private static final int ID_PARAMETER_INDEX = 1;
    private static final int TITLE_PARAMETER_INDEX = 1;
    private static final int PRICE_PARAMETER_INDEX = 2;
    private static final int UPDATE_ID_PARAMETER_INDEX = 3;
    private static final int ZERO_AFFECTED_ROW = 0;
    private static final int ONE_AFFECTED_ROW = 1;
    private static final String ID_ROW_LABEL = "id";
    private static final String TITLE_ROW_LABEL = "title";
    private static final String PRICE_ROW_LABEL = "price";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO book(title, price) VALUES(?, ?)";
        try (Connection connection = MySqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_PARAMETER_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_PARAMETER_INDEX, book.getPrice());
            if (statement.executeUpdate() < ONE_AFFECTED_ROW) {
                throw new DataProcessingException("Book was not created: " + book);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Cannot create new book, something gone wrong: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = MySqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(ID_PARAMETER_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = parseToObject(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get Book from database by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM book";
        List<Book> resultList = new ArrayList<>();
        try (Connection connection = MySqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = parseToObject(resultSet);
                resultList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get books from database", e);
        }
        return resultList;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE book SET title= ?, price= ? WHERE id = ?";
        try (Connection connection = MySqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(TITLE_PARAMETER_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_PARAMETER_INDEX, book.getPrice());
            statement.setLong(UPDATE_ID_PARAMETER_INDEX, book.getId());
        } catch (SQLException e) {
            throw new DataProcessingException("Book was not updated: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM book WHERE id =?";
        try (Connection connection = MySqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(ID_PARAMETER_INDEX, id);
            return statement.executeUpdate() > ZERO_AFFECTED_ROW;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book by id " + id, e);
        }
    }

    private Book parseToObject(ResultSet resultSet) throws SQLException {
        long id = resultSet.getObject(ID_ROW_LABEL, Long.class);
        String title = resultSet.getString(TITLE_ROW_LABEL);
        BigDecimal price = resultSet.getBigDecimal(PRICE_ROW_LABEL);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
