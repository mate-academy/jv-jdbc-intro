package mate.academy.dao;

import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int ID_INDEX = 1;
    private static final int ID_POSITION = 3;
    private static final int PRICE_POSITION = 2;
    private static final int TITLE_POSITION = 1;
    
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(TITLE_POSITION, book.getTitle());
            preparedStatement.setBigDecimal(PRICE_POSITION, book.getPrice());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a book: " + book + " to DB", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(ID_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            Book foundBook = null;
            if (resultSet.next()) {
                foundBook = processResultSet(resultSet);
            }
            return Optional.ofNullable(foundBook);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book recievedBook = processResultSet(resultSet);
                books.add(recievedBook);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all data from DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(TITLE_POSITION, book.getTitle());
            statement.setObject(PRICE_POSITION, book.getPrice());
            statement.setObject(ID_POSITION, book.getId());
            statement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update such book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(ID_INDEX, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private static Book processResultSet(ResultSet resultSet) {
        Book recievedBook = new Book();
        try {
            recievedBook.setId(resultSet.getObject("id", Long.class));
            recievedBook.setTitle(resultSet.getString("title"));
            recievedBook.setPrice(resultSet.getObject("price", BigDecimal.class));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't process ResultSet", e);
        }
        return recievedBook;
    }
}
