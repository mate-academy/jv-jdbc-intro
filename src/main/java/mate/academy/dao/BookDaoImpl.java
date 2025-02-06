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
    public static final int FIRST_INDEX = 1;
    public static final int SECOND_INDEX = 2;
    public static final int THIRD_INDEX = 3;
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String PRICE = "price";
    public static final String CREATE_SQL_REQUEST =
            "INSERT INTO books (title, price) VALUES (?, ?)";
    public static final String FIND_BY_ID_SQL_REQUEST =
            "SELECT * FROM books WHERE id = ?";
    public static final String FIND_ALL_SQL_REQUEST =
            "SELECT * FROM books";
    public static final String UPDATE_SQL_REQUEST =
            "UPDATE books SET title = ?, price = ? WHERE id = ?";
    public static final String DELETE_BY_ID_SQL_REQUEST =
            "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        CREATE_SQL_REQUEST, Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(FIRST_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX, book.getPrice());

            if (statement.executeUpdate() < FIRST_INDEX) {
                throw new RuntimeException("Expected to insert 1 record but inserted 0 rows");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_INDEX, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        FIND_BY_ID_SQL_REQUEST
                )
        ) {
            statement.setLong(FIRST_INDEX, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getObject(TITLE, String.class);
                BigDecimal price = resultSet.getObject(PRICE, BigDecimal.class);

                Book book = new Book();
                book.setId(id);
                book.setPrice(price);
                book.setTitle(title);

                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL_REQUEST)
        ) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getObject(ID, Long.class);
                String title = resultSet.getObject(TITLE, String.class);
                BigDecimal price = resultSet.getObject(PRICE, BigDecimal.class);

                Book book = new Book();
                book.setId(id);
                book.setPrice(price);
                book.setTitle(title);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_SQL_REQUEST,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(FIRST_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX, book.getPrice());
            statement.setLong(THIRD_INDEX, book.getId());

            if (statement.executeUpdate() < FIRST_INDEX) {
                throw new RuntimeException("Expected to insert 1 record but inserted 0 rows");
            }

            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            return false;
        }

        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL_REQUEST)
        ) {
            statement.setLong(FIRST_INDEX, id);
            if (statement.executeUpdate() < FIRST_INDEX) {
                return false;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id " + id, e);
        }
        return true;
    }
}
