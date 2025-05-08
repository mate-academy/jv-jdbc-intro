package mate.academy.util.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.util.ConnectionUtil;
import mate.academy.util.domain.Book;
import mate.academy.util.exception.DataProcessingException;
import mate.academy.util.lib.Dao;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_BOOK_QUERY = "INSERT INTO book (id, title, price) "
            + "VALUES (?, ?, ?)";
    private static final String FIND_BOOK_BY_ID_QUERY = "SELECT * FROM book WHERE id = ?";
    private static final String FIND_ALL_BOOK_QUERY = "SELECT * FROM book";
    private static final String UPDATE_BOOK_QUERY = "UPDATE book SET title = ?, "
            + "price = ? WHERE id = ?";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM book WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.connectToDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_BOOK_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setBigDecimal(3, book.getPrice());
            preparedStatement.execute();
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to insert at least 1 row, but was 0");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getObject(1, Long.class);
                    book.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a new book" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(int id) {
        Optional<Book> findBookById = Optional.empty();
        try (Connection connection = ConnectionUtil.connectToDatabase();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(FIND_BOOK_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                findBookById = Optional.of(mapResutSetToBook(resultSet, id));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id" + id, e);
        }
        return findBookById;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.connectToDatabase();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(FIND_ALL_BOOK_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long bookId = resultSet.getLong("id");
                books.add(mapResutSetToBook(resultSet, bookId));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        Book updatedBook = book;
        try (Connection connection =
                     ConnectionUtil.connectToDatabase();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(UPDATE_BOOK_QUERY)) {
            preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setBigDecimal(3, book.getPrice());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                updatedBook.setTitle(title);
                updatedBook.setPrice(price);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
        return updatedBook;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection connection = ConnectionUtil.connectToDatabase();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(DELETE_BOOK_QUERY)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private Book mapResutSetToBook(ResultSet resultSet, long id) {
        Book book;
        try {
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");
            book = new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't map result set to book: " + resultSet, e);
        }
        return book;
    }
}

