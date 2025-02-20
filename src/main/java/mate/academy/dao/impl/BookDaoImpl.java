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
import mate.academy.dao.BookDao;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sqlCreate = "INSERT INTO books (title, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCreate,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            updateBookInDb(preparedStatement);
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1,Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create book", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlFindAll = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlFindAll)) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBookFromDb(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't find book by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sqlFindAll = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlFindAll)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(getBookFromDb(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sqlCreate = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        String sqlFind = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCreate);
                PreparedStatement selectStatement = connection.prepareStatement(sqlFind)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            updateBookInDb(preparedStatement);
            selectStatement.setLong(1, book.getId());
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                return getBookFromDb(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't update book", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlFindById = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlFindById)) {
            preparedStatement.setString(1, id.toString());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to delete at least one row,"
                        + " but deleted 0 rows");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete book by id", e);
        }
        return true;
    }

    private Book getBookFromDb(ResultSet resultSet) throws SQLException {
        Long rowId = resultSet.getObject("id", Long.class);
        String rowTitle = resultSet.getObject("title", String.class);
        BigDecimal rowPrice = resultSet.getObject("price", BigDecimal.class);
        Book newBook = new Book();
        newBook.setId(rowId);
        newBook.setTitle(rowTitle);
        newBook.setPrice(rowPrice);
        return newBook;
    }

    private void updateBookInDb(PreparedStatement preparedStatement)
            throws SQLException {
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows < 1) {
            throw new RuntimeException("Expected to modify at least one row, but modified 0 rows");
        }
    }
}
