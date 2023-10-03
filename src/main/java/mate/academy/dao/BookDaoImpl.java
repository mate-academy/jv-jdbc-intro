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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books(title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create book entity", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Book> bookList = mapToBook(resultSet);
            if (bookList.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(bookList.get(0));
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> bookList = mapToBook(resultSet);
            return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update the book " + book.getTitle(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book by id " + id, e);
        }
    }

    private List<Book> mapToBook(ResultSet resultSet) {
        List<Book> bookList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("ResultSet error occurred", e);
        }
        return bookList;
    }
}
