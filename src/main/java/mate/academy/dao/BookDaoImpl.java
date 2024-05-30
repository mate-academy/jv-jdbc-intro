package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) values (?, ?)";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to insert at least one row, but inserted 0 rows."
                );
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            connection.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create new book: " + book);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(extractBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not fetch the book with the provided ID: " + id);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(extractBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not fetch books");
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            connection.close();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to update two rows, but updated 0 rows."
                );
            } else {
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update the book: " + book);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Book> deletingBook = findById(id);
        if (deletingBook.equals(Optional.empty())) {
            throw new DataProcessingException(
                    "Impossible to delete the book with provided ID: "
                            + id
                            + ", because there isn't this book in the DB"
            );
        }

        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can not delete the book with the provided ID: " + id
            );
        }
    }

    private Book extractBook(ResultSet resultSet) {
        try {
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");
            Long id = (long) resultSet.getObject("id");
            return new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Impossible to extract the book from the ResultSet."
            );
        }
    }
}
