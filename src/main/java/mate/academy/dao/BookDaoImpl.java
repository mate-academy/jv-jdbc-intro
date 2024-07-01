package mate.academy.dao;

import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int FIRST_QUESTION_MARK_INDEX = 1;
    private static final int SECOND_QUESTION_MARK_INDEX = 2;
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String ADD_LINE = " ";
    private static final String INIT_DB_SQL = "src/main/resources/init_db.sql";
    private static final String TAKE_BOOK_FROM_DB = "SELECT * FROM book WHERE id = ?";
    private static final String DELETE_BOOK_FROM_DB = "DELETE * FROM book WHERE id = ?";
    private static final String TAKE_BOOKS_FROM_DB = "SELECT * FROM book";
    private static final String SAVE_BOOK_IN_DB = "INSERT INTO book (title, price) VALUES (?, ?)";

    @Override
    public void createTable() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sqlCodeForTable = Files.readAllLines(Path.of(INIT_DB_SQL))
                    .stream()
                    .collect(Collectors.joining(ADD_LINE));

            PreparedStatement statement = connection.prepareStatement(sqlCodeForTable);
            statement.executeUpdate();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book save(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_BOOK_IN_DB,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(FIRST_QUESTION_MARK_INDEX,
                    book.getTitle());
            statement.setBigDecimal(SECOND_QUESTION_MARK_INDEX,
                    book.getPrice());
            int end = statement.executeUpdate();
            if (end < 1) {
                throw new RuntimeException("Failed to save book");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_QUESTION_MARK_INDEX, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
        return null;
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

                Book book = new Book(idFromBook, titleFromBook, priceFromBook);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_FROM_DB)) {

            statement.setLong(FIRST_QUESTION_MARK_INDEX, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Book update(Book book) {
        long bookID = book.getId();
        boolean deleteById = deleteById(bookID);
        if (deleteById) {
            save(book);
        }
        return book;
    }
}
