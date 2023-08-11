package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

@mate.academy.lib.Dao
public class BookDaoImpl implements BookDao {
    private static final String TABLE_NAME = "books";
    private static final String TITLE_NAME = "title";
    private static final String PRICE_NAME = "price";
    private static final int FIRST_INDEX = 1;
    private static final int SECOND_INDEX = 2;
    private static final int THIRD_INDEX = 3;
    private static final int ROW_EXIST_NUM = 1;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO " + TABLE_NAME + " (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            validateTableConnection(connection, TABLE_NAME);
            statement.setString(FIRST_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < ROW_EXIST_NUM) {
                throw new DataProcessingException("Expected to insert at leas one row, but inserted 0 row.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            validateTableConnection(connection, TABLE_NAME);
            statement.setLong(FIRST_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(TITLE_NAME);
                BigDecimal price = resultSet.getObject(PRICE_NAME, BigDecimal.class);
                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create a connection to the DB" ,e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Book> allBooks = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getObject(FIRST_INDEX, Long.class);
                String title = resultSet.getString(TITLE_NAME);
                BigDecimal price = resultSet.getObject(PRICE_NAME, BigDecimal.class);
                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                allBooks.add(book);
            }
            return allBooks;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create a connection to the DB" ,e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE " + TABLE_NAME + " SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            validateTableConnection(connection, TABLE_NAME);
            statement.setString(FIRST_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX, book.getPrice());
            statement.setLong(THIRD_INDEX, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < ROW_EXIST_NUM) {
                throw new DataProcessingException("Expected to insert at leas one row, but inserted 0 row.");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update new book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            validateTableConnection(connection, TABLE_NAME);
            statement.setLong(FIRST_INDEX, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < ROW_EXIST_NUM) {
                throw new DataProcessingException("Expected to insert at leas one row, but inserted 0 row.");
            }
            return affectedRows >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private static boolean tableExists(Connection connection ,String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (var resultSet = metaData.getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }

    private void validateTableConnection(Connection connection, String tableName) throws SQLException {
        if (connection == null) {
            throw new DataProcessingException("Connection can't be null");
        }
        if (tableName == null) {
            throw new DataProcessingException("TableName can't be null");
        }
        if (tableName.isEmpty()) {
            throw new DataProcessingException("TableName can't be empty");
        }
        if (!tableExists(connection, tableName)) {
            throw new DataProcessingException("Table: " + tableName + " is not exist");
        }
    }
}
