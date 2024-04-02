package mate.academy.dao;

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
import mate.academy.models.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int MIN_AFFECTED_ROWS_COUNT = 1;
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    @Override
    public Book create(Book book) {
        String sqlRequest = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest,
                            Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getPrice().intValue());

            int affectedRows = preparedStatement.executeUpdate();
            checkAffectedRowsNumber(affectedRows);
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create instance of book:"
                    + book.toString()
                    + " and add it to DB", e);
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        String sqlRequest = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                books.add(parseBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all book instances.", e);
        }
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlRequest = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(parseBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id. ID=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Book update(Book book) {
        String sqlRequest = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getPrice().intValue());
            preparedStatement.setLong(3, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            checkAffectedRowsNumber(affectedRows);
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to "
                    + "update data in DB. Parameters: Book="
                    + book.toString(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlRequest = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete book from DB. "
                    + "Book id = " + id, e);
        }
    }

    private void checkAffectedRowsNumber(int number) {
        if (number < MIN_AFFECTED_ROWS_COUNT) {
            throw new DataProcessingException("Excepted to change at least 1 row. "
                    + "But changed 0 rows.");
        }
    }

    private Book parseBook(ResultSet requestResult) {
        try {
            Long id = requestResult.getObject(ID, Long.class);
            String title = requestResult.getString(TITLE);
            int price = requestResult.getInt(PRICE);
            return new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse book "
                    + "data from resultSet", e);
        }
    }
}
