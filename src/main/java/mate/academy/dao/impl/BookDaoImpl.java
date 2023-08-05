package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
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
    @Override
    public Book create(Book book) {
        String createQuery = "INSERT INTO books(title, price) "
                            + "VALUES(?, ?);";

        try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement =
                    connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, book.getTitle());
            createStatement.setBigDecimal(2, book.getPrice());
            createStatement.executeUpdate();
            ResultSet generatedKeys = createStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create and add book to DB" + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findQuery = "SELECT * "
                + "FROM books "
                + "WHERE id = ? AND is_deleted = FALSE;";

        try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement findStatement = connection.prepareStatement(findQuery)) {
            findStatement.setLong(1, id);
            ResultSet resultSet = findStatement.executeQuery();
            Book book = null;

            if (resultSet.next()) {
                book = getBookFromResultSet(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String findAllQuery = "SELECT * "
                + "FROM books "
                + "WHERE is_deleted = FALSE;";

        try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement findAllStatement = connection.prepareStatement(findAllQuery)) {
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of books from DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE books "
                + "SET title = ?, price = ? "
                + "WHERE id = ?;";

        try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, book.getTitle());
            updateStatement.setBigDecimal(2, book.getPrice());
            updateStatement.setLong(3, book.getId());
            int affectedRows = updateStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("Expected it update at leas one row, but update 0 rows");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book by id: " + book.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE books " 
                + "SET is_deleted = TRUE " 
                + "WHERE id = ?;";
        
        try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book from DB dy id: " + id, e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(id, title, price);
    }
}
