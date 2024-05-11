package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public class BookDaoImpl implements BookDaoInt {
    public static final String URL = "jdbc:mysql://localhost:3306/book";
    public static final String USER = "root";
    public static final String PASSWORD = "1111";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows == 0) {
                throw new RuntimeException("Creating book failed, no rows affected.");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Runtime exception while creating book."
                    + " Can't create book", e);
        } catch (SQLException e) {
            throw new RuntimeException("SQL error while creating book."
                    + " Can't create book", e);
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                books.add(mapBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL error while creating book. Can't find book", e);
        }
        return books;
    }

    private Book mapBookFromResultSet(ResultSet resultSet) {
        try {
            Book book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setTitle(resultSet.getString("title"));
            book.setPrice(resultSet.getBigDecimal("price"));
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("SQL error db book.", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Sql error while finding book",e);
        }
        return Optional.empty();
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Sql error while updating book",e);
        }
        throw new RuntimeException("Book not found");
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Sql error while deleting book",e);
        }
    }
}
