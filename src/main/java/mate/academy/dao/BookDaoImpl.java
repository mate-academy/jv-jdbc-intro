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
import mate.academy.ConnectionUtil;
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static Connection connection;
    private static final String INSERT_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";

    public BookDaoImpl() {
        connection = ConnectionUtil.getConnection();
    }

    @Override
    public Book create(Book book) {
        checkBook(book);
        if (book.getId() != null) {
            throw new RuntimeException("Book id must be null");
        }
        try (PreparedStatement statement = connection.prepareStatement(
                INSERT_QUERY, Statement.RETURN_GENERATED_KEYS
        )) {
            statementSet(statement, book);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to insert at least one row, but inserted 0 rows."
                );
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        checkId(id);
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id:" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(resultBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        checkBook(book);
        checkId(book.getId());
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statementSet(statement, book);
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Book with id " + book.getId() + " not found.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        checkId(id);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private void checkId(Long id) {
        if (id <= 0) {
            throw new RuntimeException("ID can't be zero or negative");
        }
    }

    private void checkBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (book.getTitle() == null) {
            throw new IllegalArgumentException("Title is null");
        }
    }

    private Book resultBook(ResultSet resultSet) {
        try {
            return new Book(
                    resultSet.getObject("id", Long.class),
                    resultSet.getString("title"),
                    resultSet.getObject("price", BigDecimal.class)
                    );
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get the book", e);
        }
    }

    private void statementSet(PreparedStatement statement, Book book) {
        try {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't to set statement", e);
        }
    }
}
