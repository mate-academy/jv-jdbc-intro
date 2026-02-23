package mate.academy.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.conection.DBconnection;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection con = DBconnection.getConnection()) {
            PreparedStatement preparedStatement =
                    con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getLong(1));
            }
            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = DBconnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }

            Book book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setTitle(resultSet.getString("title"));
            book.setPrice(resultSet.getBigDecimal("price"));
            return Optional.of(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        try (Connection connection = DBconnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = DBconnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            preparedStatement.execute();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DBconnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id " + id, e);
        }
    }
}
