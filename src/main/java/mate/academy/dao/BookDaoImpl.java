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
import mate.academy.domain.Book;
import mate.academy.lib.Dao;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_BOOK_QUERY = "INSERT INTO book (title, price) VALUES (?, ?)";
    private static final String FIND_BOOK_BY_ID_QUERY = "SELECT * FROM book WHERE id = ?";
    private static final String FIND_ALL_BOOK_QUERY = "SELECT * FROM book";
    private static final String UPDATE_BOOK_QUERY = "UPDATE book SET title = ?, "
            + "price = ? WHERE id = ?";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM book WHERE id = ?";

    @Override
    public Book create(Book book) {
        Book createdBook;
        try (Connection connection = ConnectionUtil.connectToDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_BOOK_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setBigDecimal(3, book.getPrice());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                createdBook = new Book(
                        resultSet.getString("title"),
                        resultSet.getBigDecimal("price"));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return createdBook;
    }

    @Override
    public Optional<Book> findById(int id) {
        Book findBookById = null;
        try (Connection connection = ConnectionUtil.connectToDatabase();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(FIND_BOOK_BY_ID_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long bookId = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                findBookById = new Book(bookId, title, price);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return Optional.ofNullable(findBookById);
    }

    @Override
    public List<Book> findAll() {
        List<Book> foundBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.connectToDatabase();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(FIND_ALL_BOOK_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long bookId = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                foundBooks.add(new Book(bookId, title, price));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return foundBooks;
    }

    @Override
    public Book update(Book book) {
        Book updatedBook = book;
        try (Connection connection = ConnectionUtil.connectToDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setBigDecimal(3, book.getPrice());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                updatedBook.setTitle(title);
                updatedBook.setPrice(price);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return updatedBook;
    }

    @Override
    public boolean deleteById(int id) {
        boolean isDeleted;
        try (Connection connection = ConnectionUtil.connectToDatabase();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(DELETE_BOOK_QUERY)) {
            preparedStatement.setLong(1, id);
            isDeleted = preparedStatement.execute();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return isDeleted;
    }
}

