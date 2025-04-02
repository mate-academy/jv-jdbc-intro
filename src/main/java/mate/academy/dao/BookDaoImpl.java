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
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.model.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM books";
    private static final String SELECT_BOOK = "SELECT * FROM books WHERE id = ?";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE id = ?";
    private static final String INSERT_BOOK = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;
    private static final int THIRD_PARAMETER = 3;
    private static final String FILE_NAME = "init_db.sql";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String ID = "id";
    private static final ReadSqlScriptDao readSqlScriptDao = new ReadSqlScriptDaoImp();
    private static final String SQL_CREATE_TABLE = readSqlScriptDao.readSqlScript(FILE_NAME);

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK,
                    Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(FIRST_PARAMETER, book.getTitle());
                preparedStatement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("Book inserted successfully.");

                if (affectedRows == FIRST_PARAMETER) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            book.setId(generatedKeys.getLong(FIRST_PARAMETER));
                        }
                    }
                }
            }
            return book;
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "Failed to create book with title '%s' and price '%s'.",
                    book.getTitle(), book.getPrice());
            throw new DataProcessingException(errorMessage, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK)) {
            preparedStatement.setLong(FIRST_PARAMETER, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(TITLE);
                BigDecimal price = resultSet.getBigDecimal(PRICE);
                Book book = new Book(id, title, price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book by this id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> arrayBook = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement preparedStatement = connection
                           .prepareStatement(SELECT_ALL_BOOKS);
                   ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Long id = resultSet.getObject(ID, Long.class);
                String title = resultSet.getString(TITLE);
                BigDecimal price = resultSet.getBigDecimal(PRICE);
                Book book = new Book(id, title, price);

                arrayBook.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find table", e);
        }
        return arrayBook;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK)) {
            preparedStatement.setString(FIRST_PARAMETER, book.getTitle());
            preparedStatement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            preparedStatement.setLong(THIRD_PARAMETER, book.getId());
            int effectedRows = preparedStatement.executeUpdate();
            if (effectedRows < FIRST_PARAMETER) {
                throw new RuntimeException("Expected to insert at least one row, but inserted: "
                        + effectedRows);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not updated book", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(DELETE_BOOK)) {
            preparedStatement.setLong(FIRST_PARAMETER, id);
            int effectedRows = preparedStatement.executeUpdate();
            if (effectedRows < FIRST_PARAMETER) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book by this id: " + id, e);
        }
    }

    static {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            statement.execute(SQL_CREATE_TABLE);
            System.out.println("Table created successfully (if it doesn't exist).");
        } catch (SQLException e) {
            throw new DataProcessingException("Error while initializing the database", e);
        }
    }
}
