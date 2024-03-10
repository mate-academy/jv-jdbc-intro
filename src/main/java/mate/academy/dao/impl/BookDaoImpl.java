package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_QUERY =
            "INSERT INTO book(title, price) VALUES (?,?)";
    private static final String FIND_BY_ID_QUERY =
            "SELECT * FROM book WHERE id = ?";
    private static final String FIND_ALL_QUERY =
            "SELECT * FROM book";
    private static final String UPDATE_BOOK_QUERY =
            "UPDATE book SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BOOK_QUERY =
            "DELETE FROM book WHERE id = ?";
    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected < 1) {
                throw new RuntimeException("Book wasn't inserted into database: " + book);
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long generatedId = generatedKeys.getObject(1, Long.class);
                return findById(generatedId).orElseThrow(
                        () -> new RuntimeException("Can't find book by id: " + generatedId));
            }
            return null;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't perform create query: " + CREATE_QUERY, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(FIND_BY_ID_QUERY)){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBookEntity(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't perform find by id query: " + FIND_BY_ID_QUERY, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(getBookEntity(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't perform findAll query: " + FIND_ALL_QUERY, e);
        }
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("Book's id can't be null");
        }
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(UPDATE_BOOK_QUERY)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected < 1 ) {
                throw new RuntimeException(
                        String.format("Book '%s' update was unsuccessful, rows affected = %d",
                                book, rowsAffected));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't perform update query: " + UPDATE_BOOK_QUERY, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_QUERY)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't perform delete query: " + DELETE_BOOK_QUERY, e);
        }
    }

    private Book getBookEntity(ResultSet resultSet) {
        try {
            Long bookId = resultSet.getObject("id", Long.class);
            String bookTitle = resultSet.getObject("title", String.class);
            BigDecimal bookPrice = resultSet.getObject("price", BigDecimal.class);
            return new Book(bookId, bookTitle, bookPrice);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
