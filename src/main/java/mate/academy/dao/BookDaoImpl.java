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
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int ZERO_UPDATES = 0;
    private static final int ID_INDEX = 1;
    private static final int TITLE_INDEX = 2;
    private static final int PRICE_INDEX = 3;
    private static final int TITLE_INDEX_FOR_UPDATE_OPERATION = 1;
    private static final int PRICE_INDEX_FOR_UPDATE_OPERATION = 2;
    private static final int ID_INDEX_FOR_UPDATE_OPERATION = 3;
    private static final String ID_COLUMN = "books.id";
    private static final String TITLE_COLUMN = "books.title";
    private static final String PRICE_COLUMN = "books.price";
    private static final String NO_UPDATES_EXCEPTION_TEXT = "Expected to update at least 1 value, but updated 0 values.";
    private static final String CREATING_BOOK_EXCEPTION_TEXT = "Can't save a book ";
    private static final String FINDING_BOOK_BY_ID_EXCEPTION_TEXT = "Can't get a book by id = ";
    private static final String GETTING_LIST_OF_ALL_BOOKS_EXCEPTION_TEXT = "Can't get a book list from db";
    private static final String UPDATING_BOOK_EXCEPTION_TEXT = "Can't update book ";
    private static final String DELETING_BOOK_BY_ID_EXCEPTION_TEXT = "Can't delete a book by id = ";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO books (id, title, price) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(ID_INDEX, book.getId());
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());

            if (statement.executeUpdate() == ZERO_UPDATES) {
                throw new RuntimeException(NO_UPDATES_EXCEPTION_TEXT);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer id = generatedKeys.getObject(ID_INDEX, Integer.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CREATING_BOOK_EXCEPTION_TEXT + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE id = ?")) {

            statement.setLong(ID_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseBookFromQuery(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(FINDING_BOOK_BY_ID_EXCEPTION_TEXT + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM books")) {

             ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(parseBookFromQuery(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException(GETTING_LIST_OF_ALL_BOOKS_EXCEPTION_TEXT, e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE books SET title = ?, price = ? WHERE id = ?")) {

            statement.setString(TITLE_INDEX_FOR_UPDATE_OPERATION, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX_FOR_UPDATE_OPERATION, book.getPrice());
            statement.setLong(ID_INDEX_FOR_UPDATE_OPERATION, book.getId());

            if (statement.executeUpdate() == ZERO_UPDATES) {
                throw new RuntimeException(NO_UPDATES_EXCEPTION_TEXT);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(UPDATING_BOOK_EXCEPTION_TEXT + book, e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE id = ?")) {

            statement.setLong(ID_INDEX, id);

            return statement.executeUpdate() > ZERO_UPDATES;
        } catch (SQLException e) {
            throw new DataProcessingException(DELETING_BOOK_BY_ID_EXCEPTION_TEXT + id, e);
        }
    }

    private Book parseBookFromQuery(ResultSet resultSet) throws SQLException{
        Book book = new Book();
        book.setId(resultSet.getObject(ID_COLUMN, Long.class));
        book.setTitle(resultSet.getObject(TITLE_COLUMN, String.class));
        book.setPrice(resultSet.getObject(PRICE_COLUMN, BigDecimal.class));
        return book;
    }
}
