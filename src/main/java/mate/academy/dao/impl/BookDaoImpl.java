package mate.academy.dao.impl;

import mate.academy.connection.ConnectionUtil;
import mate.academy.dao.BookDao;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public void create(Book book) {
        try(Connection connection = ConnectionUtil.connect()){
            String sqlInsert = "INSERT INTO books(book_title, book_price) values(?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,book.getTitle());
            statement.setBigDecimal(2,book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row,but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Book> findById(Long id) {
        Book returnedBook = new Book();
        try(Connection connection = ConnectionUtil.connect()) {
            String sqlFindById = "SELECT * FROM books WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlFindById);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long bookId = resultSet.getObject("id", Long.class);
                String bookName = resultSet.getObject("book_title",String.class);
                BigDecimal bookPrice = resultSet.getObject("book_price", BigDecimal.class);

                returnedBook.setId(bookId);
                returnedBook.setTitle(bookName);
                returnedBook.setPrice(bookPrice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(returnedBook);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try(Connection connection = ConnectionUtil.connect()) {
            String sqlFindAll = "SELECT * FROM books";
            PreparedStatement statement = connection.prepareStatement(sqlFindAll);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();

                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("book_title"));
                book.setPrice(resultSet.getBigDecimal("book_price"));

                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try(Connection connection = ConnectionUtil.connect()) {
            String sqlUpdateByName = "UPDATE books SET book_title = ?, book_price = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlUpdateByName);
            statement.setString(1,book.getTitle());
            statement.setBigDecimal(2,book.getPrice());
            statement.setLong(3, book.getId());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                return book;
            } else {
                throw new RuntimeException("Failed to update the book with ID: " + book.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try(Connection connection = ConnectionUtil.connect()) {
            String sqlDelete = "DELETE FROM books WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlDelete);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();

           return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Delete book failed: id = " + id);
        }
    }
}
