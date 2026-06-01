package mate.academy;

import mate.academy.lib.Dao;
import mate.academy.lib.ConnectionUtil;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_BOOK_QUERY =
            "INSERT INTO books (title, price) VALUES (?, ?)";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     CREATE_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a book " + book, e);
        }
    }

    @Override
    public Optional<Book> findbyId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return List.of();
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
