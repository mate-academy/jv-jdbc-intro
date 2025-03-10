package mate.academy.dao.impl;

import mate.academy.Exception.DataProcessingException;
import mate.academy.dao.BookDao;
import mate.academy.dao.ConnectionToDB;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {
    private static final String CREATE_SQL = "INSERT INTO book(title, price) VALUES (? , ?)";
    private static final String FIND_BY_ID = "SELECT * FROM book WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM book;";
    private static final String UPDATE = "UPDATE book SET title = ?, price = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM book WHERE id = ?";


    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionToDB.connectToDB();
             PreparedStatement preparedStatement
                 = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int update = preparedStatement.executeUpdate();
            if (update < 1) {
                throw new DataProcessingException("Can not insert book",new RuntimeException());
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not create book object", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = new Book();
        try(Connection connection = ConnectionToDB.connectToDB();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book.setTitle(resultSet.getObject(2,String.class));
                book.setPrice(resultSet.getObject(3, BigDecimal.class));
                book.setId(resultSet.getObject(1, Long.class));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Did not find book by id",e);
        }
        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionToDB.connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();

                book.setTitle(resultSet.getObject(2,String.class));
                book.setPrice(resultSet.getObject(3, BigDecimal.class));
                book.setId(resultSet.getObject(1, Long.class));

                books.add(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not find all books",e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionToDB.connectToDB();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            Long id = book.getId();
            BigDecimal price = book.getPrice();
            String title = book.getTitle();

            preparedStatement.setBigDecimal(3,price);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, title);

            int update = preparedStatement.executeUpdate();
            if (update < 1) {
                throw new DataProcessingException("Information is not updated", new RuntimeException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update information",e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        int result = 0;
        try (Connection connection = ConnectionToDB.connectToDB();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);

            int update = preparedStatement.executeUpdate();
            result = update;

            if (!(update > 0)) {
                throw new DataProcessingException("Nothing was deleted", new RuntimeException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Book can not be deleted",e);
        }
        return result > 0;
    }
}
