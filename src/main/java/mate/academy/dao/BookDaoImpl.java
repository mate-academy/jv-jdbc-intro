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
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.model.DataProcessingException;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int FIRST_QUESTION_MARK_INDEX = 1;
    private static final int SECOND_QUESTION_MARK_INDEX = 2;
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String TAKE_BOOK_FROM_DB = "SELECT * FROM books WHERE id = ?";
    private static final String DELETE_BOOK_FROM_DB = "DELETE FROM books WHERE id = ?";
    private static final String TAKE_BOOKS_FROM_DB = "SELECT * FROM books";
    private static final String SAVE_BOOK_IN_DB = "INSERT INTO books (title, price) VALUES (?, ?)";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SAVE_BOOK_IN_DB,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(FIRST_QUESTION_MARK_INDEX,
                    book.getTitle());
            statement.setBigDecimal(SECOND_QUESTION_MARK_INDEX,
                    book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Failed to save book: " + book);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_QUESTION_MARK_INDEX, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Error executing create for book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(TAKE_BOOK_FROM_DB)) {

            statement.setLong(FIRST_QUESTION_MARK_INDEX, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Long idFromBook = resultSet.getObject(ID,
                        Long.class);
                String titleFromBook = resultSet.getString(TITLE);
                BigDecimal priceFromBook = resultSet.getObject(PRICE,
                        BigDecimal.class);

                Book book = new Book(idFromBook, titleFromBook, priceFromBook);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error executing findById for id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(TAKE_BOOKS_FROM_DB)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {

                Long idFromBook = resultSet.getObject(ID,
                        Long.class);
                String titleFromBook = resultSet.getString(TITLE);
                BigDecimal priceFromBook = resultSet.getObject(PRICE,
                        BigDecimal.class);

                books.add(new Book(idFromBook, titleFromBook, priceFromBook));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Error executing findAll", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_FROM_DB)) {

            statement.setLong(FIRST_QUESTION_MARK_INDEX, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error executing deleteById for id: " + id, e);
        }
    }

    @Override
    public Book update(Book book) {
        /*if (book.getId() == null) {
            throw new DataProcessingException();
        }
        long bookID = book.getId();
        boolean deleteById = deleteById(bookID);
        if (deleteById) {
            create(book);
        }
        return book;*/
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Can't update book: " + book
                        + " - no rows in database updated");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error executing update for book: " + book, e);
        }
        return book;
    }
}
