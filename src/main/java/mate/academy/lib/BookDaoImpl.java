package mate.academy.lib;

import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int ID_INDEX = 1;
    private static final int TITLE_INDEX = 1;
    private static final int PRICE_INDEX = 2;
    private static final int ID_INDEX_TO_UPDATE = 3;
    private static final int MIN_UPDATED_ROWS_NUMBER = 1;
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < MIN_UPDATED_ROWS_NUMBER) {
                throw new RuntimeException("Expected 1 affected row, but was " + updatedRows);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT* FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(ID_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBookFromDB(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT* FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            List<Book> books = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(getBookFromDB(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books from DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            statement.setObject(ID_INDEX_TO_UPDATE, book.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < MIN_UPDATED_ROWS_NUMBER) {
                throw new RuntimeException("Expected 1 affected row, but was " + updatedRows);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book in DB" + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        int updatedRows;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(ID_INDEX, id);
            updatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to the DB", e);
        }
        return updatedRows > MIN_UPDATED_ROWS_NUMBER;
    }

    private Book getBookFromDB(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");
            return new Book(id, title, price);
        } catch (SQLException e) {
            throw new RuntimeException("Can't transform data from DB to Book object");
        }
    }
}
