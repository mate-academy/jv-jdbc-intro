package mate.academy.dao;

import mate.academy.exception.DataProcessingException;
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
    private static final int TITLE_INDEX = 1;
    private static final int PRICE_INDEX = 2;
    private static final int ID_INDEX_TO_UPDATE = 3;
    private static final int MIN_NUMBER_OF_UPDATED_ROWS = 1;
    private static final int MIN_NUMBER_OF_DELETED_ROWS = 1;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        Book createdBook = new Book();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < MIN_NUMBER_OF_UPDATED_ROWS) {
                throw new RuntimeException("The book: " + book + " wasn't created!");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX, Long.class);
                createdBook.setId(id);
                createdBook.setTitle(book.getTitle());
                createdBook.setPrice(book.getPrice());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create the book - " + book, e);
        }
        return createdBook;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(ID_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book foundBook = createBook(resultSet);
                return Optional.of(foundBook);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";

        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book foundBook = createBook(resultSet);
                books.add(foundBook);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books!", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            statement.setLong(ID_INDEX_TO_UPDATE, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < MIN_NUMBER_OF_UPDATED_ROWS) {
                throw new RuntimeException("The book: " + book + " wasn't updated!");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(ID_INDEX, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows >= MIN_NUMBER_OF_DELETED_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book with id: " + id, e);
        }
    }

    private Book createBook(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getObject("title", String.class);
            BigDecimal price = resultSet.getObject("price", BigDecimal.class);
            return new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get data from the resultSet - " + resultSet, e);
        }
    }
}
