package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String insert = "INSERT INTO books (title, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setDouble(2, book.getPrice());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                book.setId(keys.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Can`t insert book!", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String request = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getInt("price"));
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t find book by id !", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String request = "SELECT * FROM books";
        List<Book> bookList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getInt("price"));
                bookList.add(book);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Can`t get all books from DataBase! ", ex);
        }
        return bookList;
    }

    @Override
    public boolean deleteId(Long id) {
        String request = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(request)) {

            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Can`t delete book by id!", e);
        }
    }

    @Override
    public Book update(Book book) {
        String request = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setDouble(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return book;
            } else {
                throw new RuntimeException("Book with id " + book.getId() + " not found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t update book with id " + book.getId(), e);
        }
    }
}

