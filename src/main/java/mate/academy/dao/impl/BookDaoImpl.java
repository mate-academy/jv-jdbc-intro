package mate.academy.dao.impl;

import static java.sql.PreparedStatement.RETURN_GENERATED_KEYS;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import mate.academy.util.SqlConsumer;
import mate.academy.util.SqlFunction;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String SAVE_SQL = """
            INSERT INTO books (title, price) 
            VALUES(?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE books 
            SET title = ?, price = ? 
            WHERE id = ?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, title, price 
            FROM books 
            WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id,
            title,
            price 
            FROM books
            """;
    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM books
            WHERE id = ?
            """;

    @Override
    public Book save(Book book) {
        try (var connection = ConnectionUtil.getConnection();
                var preparedStatement =
                        connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setObject(2, book.getPrice());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot save book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        return executeQuery(FIND_BY_ID_SQL, ps -> ps.setLong(1, id), rs -> {
            if (rs.next()) {
                return Optional.of(mapResultSetToBook(rs));
            }
            return Optional.empty();
        });
    }

    @Override
    public List<Book> findAll() {
        return executeQuery(FIND_ALL_SQL, ps -> {
        }, rs -> {
            List<Book> bookList = new ArrayList<>();
            while (rs.next()) {
                bookList.add(mapResultSetToBook(rs));
            }
            return bookList;
        });
    }

    @Override
    public Book update(Book book) {
        executeUpdate(UPDATE_SQL, ps -> {
            ps.setString(1, book.getTitle());
            ps.setObject(2, book.getPrice());
            ps.setLong(3, book.getId());
        });
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        return executeUpdate(DELETE_BY_ID_SQL, ps -> ps.setLong(1, id));
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        var book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }

    private boolean executeUpdate(String sql, SqlConsumer<PreparedStatement> parameterSetter) {
        try (var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(sql)) {
            parameterSetter.accept(preparedStatement);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot execute update from SQL: " + sql, e);
        }
    }

    private <T> T executeQuery(String sql, SqlConsumer<PreparedStatement> parameterSetter,
                               SqlFunction<ResultSet, T> resultMapper) {
        try (var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(sql)) {
            parameterSetter.accept(preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            return resultMapper.apply(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot execute query for SQL: " + sql, e);
        }
    }
}
