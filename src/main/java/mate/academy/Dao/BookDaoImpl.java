package mate.academy.Dao;

import mate.academy.exception.DataException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao{
    private static final int ID_INDEX = 3;
    private static final int TITLE_PART = 1;
    private static final int PRICE_PART = 2;
    private static final int MINIMUM_CHANGES = 1;
    private static final int INDEX_ID = 1;
    private static final String INSERT = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM books";
    private static final String UPDATE = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_PART, book.getTitle());
            statement.setBigDecimal(PRICE_PART, book.getPrice());
            if (statement.executeUpdate() < MINIMUM_CHANGES) {
                throw new RuntimeException("Can't insert less than one row");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(INDEX_ID, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataException("Can't create book = " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(INDEX_ID, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataException("Can't find book with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(getBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataException("Unable to retrieve all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(TITLE_PART, book.getTitle());
            statement.setObject(PRICE_PART, book.getPrice());
            statement.setLong(ID_INDEX, book.getId());
        } catch (SQLException e) {
            throw new DataException("Can't update the book = " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(INDEX_ID, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataException("Can't delete book with id = " + id, e);
        }
    }

    private Book getBook(ResultSet resultSet) {
        Book book = new Book();
        try {
            book.setId(resultSet.getObject("id", Long.class));
            book.setTitle(resultSet.getString("title"));
            BigDecimal price = resultSet.getBigDecimal("price");
            book.setPrice(price != null ? price : BigDecimal.ZERO);
            return book;
        } catch (SQLException e) {
            throw new DataException("Can't get book from ResultSet", e);
        }
    }
}
