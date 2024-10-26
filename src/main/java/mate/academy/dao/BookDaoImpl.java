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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.service.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String INSERT_BOOK = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SELECT_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM books";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = ?";

    private static final int TITLE_COLUMN_INDEX = 1;
    private static final int PRICE_COLUMN_INDEX = 2;
    private static final int ID_COLUMN_INDEX = 1;

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_BOOK,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_COLUMN_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_COLUMN_INDEX, book.getPrice());
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Не вдалося створити книгу: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
            statement.setLong(ID_COLUMN_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createNewBook(
                        id,
                        resultSet.getString("title"),
                        resultSet.getBigDecimal("price")
                ));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Не вдалося знайти книгу за id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(createNewBook(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getBigDecimal("price")
                ));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Не вдалося знайти всі книги", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK)) {
            statement.setString(TITLE_COLUMN_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_COLUMN_INDEX, book.getPrice());
            statement.setLong(ID_COLUMN_INDEX + 1, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Не вдалося оновити книгу: " + book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Не вдалося оновити книгу: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_BY_ID)) {
            statement.setLong(ID_COLUMN_INDEX, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Не вдалося видалити книгу з id: " + id, e);
        }
    }

    private Book createNewBook(Long id, String title, BigDecimal price) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
