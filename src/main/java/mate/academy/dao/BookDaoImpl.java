package mate.academy.dao;

import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "select * from books where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = createBookFromDb(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find book with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "select * from books";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = createBookFromDb(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve data", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "delete from books where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            int update = preparedStatement.executeUpdate();
            if (update < 1) {
                throw new RuntimeException("Cannot delete the book with id " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private Book createBookFromDb(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setPrice(resultSet.getBigDecimal("price"));
        book.setTitle(resultSet.getString("title"));
        return book;
    }
}
