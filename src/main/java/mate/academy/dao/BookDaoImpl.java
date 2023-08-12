package mate.academy.dao;

import mate.academy.exeption.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String createBookRequest = "INSERT INTO book (title, price) values (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createBookStatement  = connection
                     .prepareStatement(createBookRequest, Statement.RETURN_GENERATED_KEYS)) {
            createBookStatement.setString(1, book.getTitle());
            createBookStatement.setDouble(2, book.getPrice());
            int effectedRows = createBookStatement.executeUpdate();
            if (effectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, but was inserted 0 row");
            }
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a book with data - " + book.toString(), e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String getBookByIdRequest = "SELECT * FROM book WHERE id = ? AND isDeleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getBookByIdStatement = connection
                     .prepareStatement(getBookByIdRequest)) {
            getBookByIdStatement.setLong(1, id);
            ResultSet resultSet = getBookByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Book(id, resultSet.getString("title"),
                        resultSet.getDouble("price")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get Book by id - " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String getAllBooksRequest = "SELECT * FROM book WHERE isDeleted = FALSE";
        List<Book> listOfBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getAllBooksStatement = connection
                     .prepareStatement(getAllBooksRequest)) {
            ResultSet resultSet = getAllBooksStatement.executeQuery();
            while (resultSet.next()) {
                listOfBooks.add(new Book(resultSet.getObject("id", Long.class),
                        resultSet.getString("title"), resultSet.getDouble("price")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant get all books" , e);
        }
        return listOfBooks;
    }

    @Override
    public Book update(Book book) {
        String deleteByIdRequest = "UPDATE book SET title = ?, price = ? WHERE id = ? AND isDeleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getBookByIdStatement = connection
                     .prepareStatement(deleteByIdRequest)) {
            getBookByIdStatement.setString(1, book.getTitle());
            getBookByIdStatement.setDouble(2, book.getPrice());
            getBookByIdStatement.setLong(3, book.getId());
            int effectedRows = getBookByIdStatement.executeUpdate();
            if (effectedRows >= 1 ) {
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book whith data - " + book, e);
        }
        return new Book();
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteByIdRequest = "UPDATE book SET isDeleted = TRUE WHERE id = ? AND isDeleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getBookByIdStatement = connection
                     .prepareStatement(deleteByIdRequest)) {
            getBookByIdStatement.setLong(1, id);
            int effectedRows = getBookByIdStatement.executeUpdate();
            if (effectedRows >= 1 ) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id - " + id, e);
        }
        return false;
    }
}
