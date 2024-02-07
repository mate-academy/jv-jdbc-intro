package mate.academy.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.connection.ConnectionUtil;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImp implements BookDao {
    private static final String COLUMN_LABEL_ID_BOOK = "id";
    private static final String COLUMN_LABEL_TITLE_BOOK = "title";
    private static final String COLUMN_LABEL_PRICE_BOOK = "price";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitleBook());
            statement.setObject(2, book.getPriceBook());

            int affectRows = statement.executeUpdate();
            if (affectRows < 1) {
                throw new DataProcessingException("Expected to insert at leas one row,"
                        + " but inserted " + affectRows + " row(s)");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long idBook = generatedKeys.getObject(1, Long.class);
                book.setIdBook(idBook);
            }
        } catch (DataProcessingException | SQLException exception) {
            throw new DataProcessingException("There is no way to add a new object of the "
                    + book + ", " + exception);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String titleBook = resultSet.getString(COLUMN_LABEL_TITLE_BOOK);
                Integer priceBook = resultSet.getObject(COLUMN_LABEL_PRICE_BOOK, Integer.class);

                Book madeBook = getReadyMadeBook(resultSet);
                return Optional.of(madeBook);
            }
        } catch (DataProcessingException | SQLException exception) {
            throw new RuntimeException("There is no way to find the Book by the id: "
                    + id + ", " + exception);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books;";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book addedBook = getReadyMadeBook(resultSet);
                books.add(addedBook);
            }
        } catch (DataProcessingException | SQLException exception) {
            throw new RuntimeException("There is no way to get all objects of the Book, "
                    + exception);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, book.getTitleBook());
            statement.setObject(2, book.getPriceBook());
            statement.setObject(3, book.getIdBook());

            int affectRows = statement.executeUpdate();
            if (affectRows < 1) {
                throw new DataProcessingException("Expected to insert at leas one row,"
                        + " but inserted " + affectRows + " row(s)");
            }

        } catch (DataProcessingException | SQLException exception) {
            throw new DataProcessingException("There is no way to update the : "
                    + book + ", " + exception);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?;";
        int affectRows;

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            affectRows = statement.executeUpdate();

        } catch (SQLException exception) {
            throw new DataProcessingException("There is no way to delete the Book by the id: "
                    + id + ", " + exception);
        }
        return affectRows < 1;
    }

    private Book getReadyMadeBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(COLUMN_LABEL_ID_BOOK);
        String title = resultSet.getString(COLUMN_LABEL_TITLE_BOOK);
        BigDecimal price = resultSet.getBigDecimal(COLUMN_LABEL_PRICE_BOOK);

        Book book = new Book();
        book.setIdBook(id);
        book.setTitleBook(title);
        book.setPriceBook(price);
        return book;
    }
}
