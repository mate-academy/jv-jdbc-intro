package mate.academy.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String PATH_TO_FILE = "src/main/resources/init_db.sql";
    private Properties dbProp;
    private String dbUrl;

    public BookDaoImpl(Properties dbProp, String dbUrl) {
        this.dbProp = dbProp;
        this.dbUrl = dbUrl;
    }

    @Override
    public Book create(Book book) {
        String query;
        try {
            List<String> list = Files.readAllLines(new File(PATH_TO_FILE).toPath());
            query = String.join(" ", list);
        } catch (IOException e) {
            throw new DataProcessingException("Can't read the file " + PATH_TO_FILE, e);
        }
        try (Connection connection = ConnectionUtil.getConnection(dbProp, dbUrl);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
            return save(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a table ", e);
        }
    }

    @Override
    public Book save(Book book) {
        String query = """
            INSERT INTO books (title, price)
            VALUES (?, ?);
                """;
        try (Connection connection = ConnectionUtil.getConnection(dbProp, dbUrl);
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getPrice().intValue());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Expected to insert at least one row, but was 0");
            }
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                book.setId(generatedKey.getObject(1, Long.class));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = """
            SELECT *
            FROM books
            WHERE id = ?;
                """;
        try (Connection connection = ConnectionUtil.getConnection(dbProp, dbUrl);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet executedQuery = statement.executeQuery();
            if (executedQuery.next()) {
                Book book = new Book();
                book.setId(id);
                book.setTitle(executedQuery.getString("title"));
                book.setPrice(executedQuery.getBigDecimal("price"));
                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = """
            SELECT *
            FROM books
            WHERE id = ?;
                """;
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection(dbProp, dbUrl);
                PreparedStatement statement = connection.prepareStatement(query)) {
            long id = 1L;
            statement.setLong(1, id);
            ResultSet executedQuery = statement.executeQuery();
            while (executedQuery.next()) {
                Book book = new Book();
                book.setId(executedQuery.getLong("id"));
                book.setTitle(executedQuery.getString("title"));
                book.setPrice(executedQuery.getBigDecimal("price"));
                books.add(book);
                id++;
                statement.setLong(1, id);
                executedQuery = statement.executeQuery();
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books ", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = """
            UPDATE books
            SET title = ?, price = ?
            WHERE id = ?;
                """;
        try (Connection connection = ConnectionUtil.getConnection(dbProp, dbUrl);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Supposed to insert at least 1 row, "
                        + "but was 0");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book by id " + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = """
            DELETE
            FROM books
            WHERE id = ?;
                """;
        try (Connection connection = ConnectionUtil.getConnection(dbProp, dbUrl);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id " + id, e);
        }
    }
}

