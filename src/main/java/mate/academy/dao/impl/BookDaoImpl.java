package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
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
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String createBookQuery = "INSERT INTO books (title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createBookStatement =
                        connection.prepareStatement(createBookQuery,
                                Statement.RETURN_GENERATED_KEYS)) {
            createBookStatement.setString(1, book.getTitle());
            createBookStatement.setBigDecimal(2, book.getPrice());
            int insertedRows = createBookStatement.executeUpdate();
            if (insertedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row, but nothing was inserted");
            }
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Can t insert the book in db: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String getByIdQuery = "SELECT * FROM books WHERE id = ? AND is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement =
                        connection.prepareStatement(getByIdQuery)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = parseBookFromResultSet(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findAll() {
        String getAllQuery = "SELECT * FROM books WHERE is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllStatement =
                     connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            List<Book> booksList = new ArrayList<>();
            while (resultSet.next()) {
                booksList.add(parseBookFromResultSet(resultSet));
            }
            return booksList;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all books from db", e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateBookQuery = "UPDATE books SET title = ?, price = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateBookStatement =
                        connection.prepareStatement(updateBookQuery)) {
            updateBookStatement.setString(1, book.getTitle());
            updateBookStatement.setBigDecimal(2, book.getPrice());
            int updatedRows = updateBookStatement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException("Rows were not updated");
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteByIdQuery = "UPDATE books SET is_deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteByIdStatement =
                        connection.prepareStatement(deleteByIdQuery)) {
                deleteByIdStatement.setLong(1, id);
                int deletedRows = deleteByIdStatement.executeUpdate();
                return deletedRows != 0;
            } catch (SQLException e) {
            throw new RuntimeException("Can't delete book by id: " + id, e);
        }
    }

    private Book parseBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }
}
