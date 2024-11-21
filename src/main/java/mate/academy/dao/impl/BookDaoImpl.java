package mate.academy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int TITLE_INDEX = 1;
    private static final int PRICE_INDEX = 2;
    private static final int GENERATED_ID_INDEX = 1;
    private static final int MIN_AFFECTED_ROWS = 1;
    private static final int ID_FIND_INDEX = 1;
    private static final String TITLE_COLUMN = "title";
    private static final String PRICE_COLUMN = "price";
    private static final String ID_COLUMN = "id";
    private static final int ID_UPDATE_INDEX = 3;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setObject(PRICE_INDEX, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < MIN_AFFECTED_ROWS) {
                throw new DataProcessingException(
                        "Expected to insert at least 1 line, but inserted 0");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(GENERATED_ID_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(ID_FIND_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = extractBook(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id: " + id, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(extractBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books from database", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(sql);

            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setObject(PRICE_INDEX, book.getPrice());
            statement.setLong(ID_UPDATE_INDEX, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < MIN_AFFECTED_ROWS) {
                throw new DataProcessingException(
                        "Expected to change at least 1 line, but changed 0");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(sql);

            statement.setLong(ID_FIND_INDEX, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows >= MIN_AFFECTED_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private Book extractBook(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getObject(ID_COLUMN, Long.class),
                resultSet.getString(TITLE_COLUMN),
                resultSet.getBigDecimal(PRICE_COLUMN)
        );
    }
}
