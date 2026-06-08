package mate.academy.model.daobook;

import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_BOOK_SQL = "INSERT INTO BOOKS(Title, Author, Isbn) VALUES(?, ?, ?)";

    @Override
    public Book create(Book book) {
        try(Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(CREATE_BOOK_SQL, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setString(2, book.getAuthor());
                preparedStatement.setString(3, book.getIsbn());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                long id = resultSet.getLong(1);
                book.setId(id);
                return book;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
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

    private Connection getConnection() {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test";
        Connection conn = null;
        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?" +
                            "user=root&password=");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }
}
