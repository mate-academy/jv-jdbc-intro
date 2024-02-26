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
    private static final String ID_COLUMN_LABEL = "id";
    private static final String TITLE_COLUMN_LABEL = "title";
    private static final String PRICE_COLUMN_LABEL = "price";

    @Override
    public Book create(Book book) {
        String insertQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                        connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, book.getTitle());
            createStatement.setBigDecimal(2, book.getPrice());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String getByIdQuery = "SELECT * FROM books "
                + "WHERE id = ? AND is_deleted IS NOT TRUE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(getByIdQuery)) {
            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parse(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String getAllQuery = "SELECT * FROM books WHERE is_deleted IS NOT TRUE";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                books.add(parse(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from table.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String updateQuery
                = "UPDATE books SET title = ?, price = ?, is_deleted = FALSE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, book.getTitle());
            updateStatement.setBigDecimal(2, book.getPrice());
            updateStatement.setLong(3, book.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery = "UPDATE books SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteByIdStatement = connection.prepareStatement(deleteQuery)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private Book parse(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(ID_COLUMN_LABEL, Long.class));
        book.setTitle(resultSet.getString(TITLE_COLUMN_LABEL));
        book.setPrice(resultSet.getBigDecimal(PRICE_COLUMN_LABEL));
        return book;
    }
}
