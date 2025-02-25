package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {
    public static final String CREATE_SQL = "INSERT INTO car(model,year) VALUES(?,?)";
    public static final String FIND_BY_ID_SQL = "SELECT * FROM car WHERE id = ?";
    public static final String FIND_ALL_SQL = "SELECT * FROM car";
    public static final String DELETE_SQL = "DELETE FROM books WHERE id = ?";
    public static final String UPDATE_SQL = "UPDATE car SET model = ?, year = ? WHERE id = ?";
    public static final String CREATE_EXCEPTION = "Error during creation of the book \"%s\"";
    public static final String FIND_BY_ID_EXCEPTION = "Error during finding the book by id \"%s\"";
    public static final String FIND_ALL_EXCEPTION = "Error during finding of list of the books \"%s\"";
    public static final String DELETE_EXCEPTION = "Error during deleting the book \"%s\"";
    public static final String UPDATE_EXCEPTION = "Error during updating the book \"%s\"";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prStatement.setString(1,book.getTitle());
            prStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = prStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(CREATE_EXCEPTION, new RuntimeException());
            }
            ResultSet generatedKeys = prStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);

            }
            return book;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                BigDecimal price = resultSet.getBigDecimal("price");
                String title = resultSet.getString("title");
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(FIND_BY_ID_EXCEPTION, new RuntimeException());
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Book book = new Book();
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");
            book.setId(resultSet.getLong("id"));
            book.setPrice(resultSet.getBigDecimal("price"));
            book.setTitle(resultSet.getString("title"));
            books.add(book);
        }
        } catch (SQLException e) {
            throw new DataProcessingException(FIND_ALL_EXCEPTION, new RuntimeException());
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(3, book.getId());
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(UPDATE_EXCEPTION, new RuntimeException());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1,id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(DELETE_EXCEPTION, new RuntimeException());
        }
    }
}
