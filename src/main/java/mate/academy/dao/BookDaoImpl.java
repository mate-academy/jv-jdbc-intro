package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import mate.academy.util.ReadSqlFile;

@Dao
public class BookDaoImpl implements BookDao {
    private ReadSqlFile readSqlFile;

    @Override
    public Book create(Book book) {
        String sqlCreateRow = "INSERT INTO books (title, price) VALUES (?, ?)";
        String tableName = "books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statementForRow = connection.prepareStatement(
                        sqlCreateRow, Statement.RETURN_GENERATED_KEYS);) {
            if (!tableExists(connection, tableName)) {
                createTable();
            }
            statementForRow.setString(1, book.getTitle());
            statementForRow.setBigDecimal(2, book.getPrice());
            int affectedRows = statementForRow.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to insert at least one row, but inserted 0 rows");
            }
            ResultSet generatedKeys = statementForRow.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can't add book to DB" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String selectQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return getEntity(resultSet, id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book by id in the DB:id-" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String selectQuery = "SELECT * FROM books";
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookList.add(getEntity(resultSet, resultSet.getLong(1)).get());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can't find books in DB", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int countUpdateRow = preparedStatement.executeUpdate();
            if (countUpdateRow > 0) {
                return findById(book.getId()).get();
            } else {
                throw new RuntimeException(
                        "Expected to update at least one row, but updated 0 rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update this book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, id);
            int countDeletedRows = preparedStatement.executeUpdate();
            if (countDeletedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can't delete row in DB by id -" + id, e);
        }
        return false;
    }

    private boolean tableExists(Connection connection, String tableName) {
        boolean isNext = false;
        DatabaseMetaData metaData = null;
        ResultSet resultSet = null;
        try {
            metaData = connection.getMetaData();
            resultSet = metaData.getTables(
                    null, null, tableName, null);
            isNext = resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isNext;
    }

    private void createTable() {
        String sqlCreateTable = readSqlFile.getQuery();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCreateTable)) {
            preparedStatement.executeQuery(sqlCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Book> getEntity(ResultSet resultSet, Long id) {
        Book book = new Book();
        String title = null;
        BigDecimal price = null;
        try {
            title = resultSet.getObject("title", String.class);
            price = resultSet.getObject("price", BigDecimal.class);
        } catch (SQLException e) {
            throw new DataProcessingException("can't create entity ", e);
        }
        book.setTitle(title);
        book.setPrice(price);
        book.setId(id);
        return Optional.ofNullable(book);
    }
}
