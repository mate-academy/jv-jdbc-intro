package mate.academy.daobook;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class DaoBookImpl implements DaoBook {

    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                "Expected to insert at least one row, but inserted 0 rows");
            }
            Optional<Book> updatedBook = findById(book.getId());
            if (updatedBook.isPresent()) {
                return updatedBook.get();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataProcessingException ex) {
            throw new RuntimeException("Can't update book" + book, ex);
        }
        return null;
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id); {
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows < 1) {
                    throw new RuntimeException(
                            "Expected to insert at least one row, but inserted 0 rows");
                }
                return true;
            }
        } catch (DataProcessingException e) {
            throw new RuntimeException("Can't delete book by id with id " + id, e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> booksArrayList = new ArrayList<>();
        String countSql = "SELECT COUNT(*) FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(countSql)) {
            ResultSet number = preparedStatement.executeQuery();
            int numberOfRows = 0;
            if (number.next()) {
                numberOfRows = Math.toIntExact(number.getLong(1));
            }

            for (long i = 1; i <= numberOfRows; i++) {
                if (findById(i).isPresent()) {
                    booksArrayList.add(findById(i).get());
                }
            }

        } catch (DataProcessingException e) {
            throw new RuntimeException("Can't findAll books", e);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return booksArrayList;
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                            "Expected to insert at least one row, but inserted 0 rows");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (DataProcessingException | SQLException e) {
            throw new RuntimeException("Can't create book" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {

        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                Long rowId = resultSet.getObject("id", Long.class);
                try {
                    boolean title = resultSet.getString("title") == null;
                } catch (DataProcessingException ex) {
                    throw new DataProcessingException("The value of title is null", ex);
                }
                String title = resultSet.getString("title");
                try {
                    boolean price = (resultSet.getString("price") == null);
                } catch (DataProcessingException exception) {
                    throw new DataProcessingException("The value of price is null", exception);
                }
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book();
                book.setId(rowId);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }
        } catch (DataProcessingException e) {
            throw new RuntimeException("Can't create connection", e);

        } catch (SQLException e) {
            throw new RuntimeException("Can't get book by id " + id, e);
        }
        return Optional.empty();
    }
}
