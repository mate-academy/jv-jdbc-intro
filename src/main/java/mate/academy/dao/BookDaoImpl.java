package mate.academy.dao;

import mate.academy.exception.DataProcessingException;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
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
    private static final int MINIMAL_OPERATION_AMOUNT = 1;

    @Override
    public Book create(Book book) {
        String insertQuery = "INSERT INTO book(title, price)" +
                " values(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement creationStatement = connection.prepareStatement(insertQuery,
                     Statement.RETURN_GENERATED_KEYS)) {
            creationStatement.setString(TITLE_INDEX, book.getTitle());
            creationStatement.setBigDecimal(PRICE_INDEX, book.getPrice());
            creationStatement.executeUpdate();
            ResultSet generatedKeys = creationStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can't get connection to db", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String selectRequest = "SELECT * FROM book WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement selectStatement =
                     connection.prepareStatement(selectRequest)) {
            selectStatement.setLong(ID_INDEX, id);
            selectStatement.executeQuery();
            ResultSet generatedKeys = selectStatement.executeQuery();
            return Optional.of(parseResultSet(generatedKeys));
        } catch (SQLException e) {
            throw new DataProcessingException("can't get book from DB " + id, e);
        }
    }

    private static Book parseResultSet(ResultSet set) {
        Book book = new Book();
        try {
            if (set.next()) {
                Long id = set.getLong("id");
                String title = set.getString("title");
                BigDecimal price = set.getBigDecimal("price");
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book from set " + set, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> allBooks = new ArrayList<>();
        String selectRequest = "SELECT * FROM Book WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); Statement getAllStatement =
                connection.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(selectRequest);
            while (resultSet.next()) {
                allBooks.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can't get all books from DB ", e);
        }
        return allBooks;
    }

    @Override
    public Book update(Book book) {
        String updateRequest = "UPDATE Book SET title = ?, price = ?"
                + " WHERE id = ? AND is_deleted = FALSE";
        int idPosition = 3;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateStatement =
                     connection.prepareStatement(updateRequest, Statement
                             .RETURN_GENERATED_KEYS)) {
            updateStatement.setLong(idPosition, book.getId());
            updateStatement.setString(TITLE_INDEX, book.getTitle());
            updateStatement.setBigDecimal(PRICE_INDEX, book.getPrice());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("can't update book: "
                    + book + " in DB ", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteRequest = "UPDATE Book SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteStatement =
                     connection.prepareStatement(deleteRequest,
                             Statement.RETURN_GENERATED_KEYS)) {
            deleteStatement.setLong(ID_INDEX, id);
            return deleteStatement.executeUpdate() >= MINIMAL_OPERATION_AMOUNT;
        } catch (SQLException e) {
            throw new DataProcessingException("can't delete book from DB with id: "
                    + id, e);
        }
    }
}
