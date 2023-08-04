package mate.academy.dao;

import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoImpl implements CarDao {
    private static final int FIRST_INDEX = 1;
    private static final int SECOND_INDEX = 2;
    private static final int THIRD_INDEX = 3;
    @Override
    public Book create(Book book) {
        String createStatement = "INSERT INTO books (title, price) VALUES (?, ?)";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement
                    = connection.prepareStatement(createStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at leas one row, but inserted 0 rows.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't crate new book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findByIdQuery = "SELECT * FROM books WHERE id = ?";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByIdQuery)) {
            statement.setLong(FIRST_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String findAllBooksQuery = "SELECT * FROM books WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement
                     = connection.prepareStatement(findAllBooksQuery)) {
            ResultSet resultSet
                    = statement.executeQuery(findAllBooksQuery);
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books from DB.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateBookQuery = "UPDATE books SET title = ?, price = ?"
                + "WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(updateBookQuery)) {
            statement.setString(FIRST_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX, book.getPrice());
            statement.setLong(THIRD_INDEX, book.getId());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteBookQuery = "UPDATE books SET is_deleted = true where id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(deleteBookQuery)) {
            statement.setLong(FIRST_INDEX, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id " + id, e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) {
        Book book = new Book();
        try {
            Long id = resultSet.getObject(FIRST_INDEX, Long.class);
            String title = resultSet.getObject("title", String.class);
            BigDecimal price = resultSet.getObject("price", BigDecimal.class);
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book from result set " + resultSet, e);
        }
        return book;
    }
}
