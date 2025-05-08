package mate.academy.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.Dao;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.DaoImpl;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@DaoImpl
public class BookDaoImpl implements Dao<Book> {
    private static final String CREATE_SQL = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM books";
    private static final String UPDATE_SQL = "UPDATE books SET title = ?,  price = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM books WHERE id = ?";
    private static final int FIRST_COLUMN_INDEX = 1;
    private static final int FIRST_PARAMETER_SQL = 1;
    private static final int SECOND_PARAMETER_SQL = 2;
    private static final int THIRD_PARAMETER_SQL = 3;
    private static final int REQUIRED_MINIMUM_OF_CHANGED_ROWS = 1;
    private static final String ID_COLUMN_LABEL = "id";
    private static final String TITLE_COLUMN_LABEL = "title";
    private static final String PRICE_COLUMN_LABEL = "price";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(CREATE_SQL,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_SQL, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER_SQL, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < REQUIRED_MINIMUM_OF_CHANGED_ROWS) {
                throw new DataProcessingException(
                        "Expected to insert at least one row, " + "but inserted 0 rows");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_COLUMN_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a new book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(FIRST_PARAMETER_SQL, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(TITLE_COLUMN_LABEL);
                BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN_LABEL);
                return Optional.of(new Book(id, title, price));
            } else {
                throw new DataProcessingException("Can't find a book by id " + id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't establish connection with the DB", e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Book> booksList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong(ID_COLUMN_LABEL);
                String title = resultSet.getString(TITLE_COLUMN_LABEL);
                BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN_LABEL);
                booksList.add(new Book(id, title, price));
            }
            if (booksList.isEmpty()) {
                throw new DataProcessingException(
                        "The DB table \"books\" is empty. Add some elements first...");
            } else {
                return booksList;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't establish connection with the DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(FIRST_PARAMETER_SQL, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER_SQL, book.getPrice());
            statement.setLong(THIRD_PARAMETER_SQL, book.getId());
            int updatedRows = statement.executeUpdate();

            if (updatedRows >= REQUIRED_MINIMUM_OF_CHANGED_ROWS) {
                return book;
            } else {
                throw new DataProcessingException("Book with id " + book.getId()
                        + " wasn't found in the DB");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book with id " + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        int updatedRows;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(FIRST_PARAMETER_SQL, id);
            updatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete a book with id " + id, e);
        }
        return updatedRows >= REQUIRED_MINIMUM_OF_CHANGED_ROWS;
    }
}
