package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String createSQLQuery = "INSERT INTO books (title,price) VALUES (?,?)";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(createSQLQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least 1 row, " +
                        "but actually got 0 rows were inserted."
                        , new RuntimeException());
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can not add book to database: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findByIdSQLQuery = "SELECT * FROM books WHERE id = ?";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByIdSQLQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                Book foundedBook = new Book();
                foundedBook.setId(id);
                foundedBook.setTitle(title);
                foundedBook.setPrice(price);
                return Optional.of(foundedBook);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can not find book by ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String findAllSQLQuery = "SELECT * FROM books";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(findAllSQLQuery)) {
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            if (resultSet.next()) {
                List<Book> books = new ArrayList<>();
                for (int i = 1; i < columnCount - 1 ; i++) {
                    Book foundedBook = new Book();
                    foundedBook.setId(resultSet.getLong("id"));
                    foundedBook.setTitle(resultSet.getString("title"));
                    foundedBook.setPrice(resultSet.getBigDecimal("price"));
                    books.add(foundedBook);
                }
                return books;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can not find any book", e);
        }
        return List.of();
    }

    @Override
    public Book update(Book book) {
        Optional<Book> foundedBook = findById(book.getId());
        if (foundedBook.isPresent()) {
            Book updatedBook = foundedBook.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setPrice(book.getPrice());
            String updateBySQL = "UPDATE books SET title = ?, price = ? WHERE id = ?";
            try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(updateBySQL)) {
                statement.setString(1, book.getTitle());
                statement.setBigDecimal(2, book.getPrice());
                statement.setLong(3, book.getId());

                int affectedRows = statement.executeUpdate();

                if (affectedRows < 1) {
                    throw new DataProcessingException("Expected to insert at least 1 row, " +
                            "but actually got 0 rows were inserted."
                            , new RuntimeException());
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can not update book: " + book , e);
            }
            return updatedBook;

        }


        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        Book foundedBook = findById(id).orElseThrow();
        String deleteSQLQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSQLQuery)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
