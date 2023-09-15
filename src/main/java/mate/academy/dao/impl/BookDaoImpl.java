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
    @Override
    public Book create(Book book) {
        String createBookQuery = "INSERT INTO books (title,price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(createBookQuery,
                         Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int rows = preparedStatement.executeUpdate();
            if (rows < 1) {
                throw new RuntimeException("Expected at least 1 inserted row, but inserted 0 rows");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save book: " + book + " to DB.", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findByIdQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String findAllBooksQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(findAllBooksQuery)) {
            List<Book> books = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(findAllBooksQuery);
            while (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books from DB.", e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateBook = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(updateBook)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Book for update doesn't exist");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteBookByIdQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteBookByIdQuery)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private static Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(1, Long.class));
        book.setTitle(resultSet.getString(2));
        book.setPrice(resultSet.getBigDecimal(3));
        return book;
    }
}
