package mate.academy.dao.impl;

import static mate.academy.util.Constants.GET_ALL_BOOKS_QUERY;
import static mate.academy.util.Constants.GET_BOOK_BY_ID_QUERY;
import static mate.academy.util.Constants.SAVE_BOOK_QUERY;
import static mate.academy.util.Constants.UPDATE_BOOK_QUERY;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        try (PreparedStatement createBookStatement = ConnectionUtil.getConnection()
                .prepareStatement(SAVE_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            createBookStatement.setString(1, book.getTitle());
            createBookStatement.setBigDecimal(2, book.getPrice());
            createBookStatement.executeUpdate();
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add book " + book + " to DB", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (PreparedStatement getBookByIdStatement =
                     ConnectionUtil.getConnection().prepareStatement(GET_BOOK_BY_ID_QUERY)) {
            getBookByIdStatement.setLong(1, id);
            ResultSet resultSet = getBookByIdStatement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = parseBookFromResultSet(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id: " + id + " from DB", e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement getAllBooksStatement =
                     ConnectionUtil.getConnection().prepareStatement(GET_ALL_BOOKS_QUERY)) {
            ResultSet resultSet = getAllBooksStatement.executeQuery();
            while (resultSet.next()) {
                books.add(parseBookFromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get books from DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement upadateBookStatment =
                     ConnectionUtil.getConnection().prepareStatement(
                             UPDATE_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            upadateBookStatment.setString(1, book.getTitle());
            upadateBookStatment.setBigDecimal(2, book.getPrice());
            upadateBookStatment.setLong(3, book.getId());
            upadateBookStatment.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book + " in DB", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement statement = ConnectionUtil.getConnection().prepareStatement(
                             "DELETE FROM books WHERE id=?", Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id + " from DB", e);
        }
    }

    private Book parseBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price")
        );
    }
}
