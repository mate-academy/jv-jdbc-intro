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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO info (book_name, author_name,"
                + " price, quantity, published_year) \n"
                + "VALUES (?, ?, ?, ?, ?);\n";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setObject(3, book.getPrice());
            statement.setInt(4, book.getQuantity());
            statement.setInt(5, book.getYear());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least one row,"
                        + " but inserted 0 rows.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a connection to the DB");
        }
        return book;
    }

    @Override
    public Book get(Long id) {
        String sql = "SELECT * FROM info WHERE id = ?";
        Book book = new Book();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String authorName = resultSet.getString("author_name");
                String bookName = resultSet.getString("book_name");
                Integer year = resultSet.getObject("published_year", Integer.class);
                BigDecimal price = resultSet.getBigDecimal("price");
                Integer quantity = resultSet.getObject("quantity", Integer.class);

                book.setAuthor(authorName);
                book.setId(id);
                book.setName(bookName);
                book.setPrice(price);
                book.setQuantity(quantity);
                book.setYear(year);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a connection to the DB");
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM info where id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = parseRow(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not connect to DB");
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM info;";
        List<Book> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = parseRow(resultSet);
                result.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not connect to the DB");
        }
        return result;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE info SET book_name = ?, author_name = ?, price = ?,"
                + " quantity = ?, published_year = ? WHERE id = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setBigDecimal(3, book.getPrice());
            statement.setLong(4, book.getQuantity());
            statement.setInt(5, book.getYear());
            statement.setLong(6, book.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("No rows updated. Book with id "
                        + book.getId() + " not found.");
            }

            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book in the DB");
        }
    }

    @Override
    public boolean delete(Book book) {
        String sql = "DELETE FROM info WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, book.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to delete at least one row,"
                        + " but deleted 0 rows.");
            }
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not connect to the DB");
        }
    }

    public Book parseRow(ResultSet resultSet) {
        try {
            Book book = new Book();
            book.setAuthor(resultSet.getString("author_name"));
            book.setName(resultSet.getString("book_name"));
            book.setPrice(resultSet.getBigDecimal("price"));
            book.setQuantity(resultSet.getObject("quantity", Integer.class));
            book.setYear(resultSet.getInt("year"));
            book.setId(resultSet.getLong("id"));
            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Can not set parameters for book");
        }
    }

}
