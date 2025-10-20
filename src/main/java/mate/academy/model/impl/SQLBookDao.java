package mate.academy.model.impl;

import mate.academy.io.ConnectionUtil;
import mate.academy.io.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.model.BookDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class SQLBookDao implements BookDao {
    @Override
    public Book create(Book book) throws DataProcessingException {
        String query = "INSERT INTO `books` (title, price) VALUES (?, ?);";
        try (Connection conn = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new SQLException("Insert statement affected rows: " + affectedRows);
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject("id", Long.class));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create book in database: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT title, price FROM `books` WHERE id = ? LIMIT 1;";
        try (Connection conn = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(id);
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new DataProcessingException("Cannot get book[" + id + "] from database", ex);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT id, title, price FROM `books`;";
        try (Connection conn = ConnectionUtil.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Book> resultList = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                resultList.add(book);
            }
            return resultList;
        } catch (SQLException ex) {
            throw new DataProcessingException("Cannot get books from database", ex);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE `books` SET title = ?, price = ? WHERE id = ?;";
        try (Connection conn = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new SQLException("Update statement affected rows: " + affectedRows);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book in database: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM `books` WHERE id = ?;";
        try (Connection conn = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setObject(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book[" + id + "] from database", e);
        }
    }
}
