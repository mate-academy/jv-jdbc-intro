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
import mate.academy.Connector;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = Connector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expended to inserted one row but it inserted 0 rows");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create connection to the DB", e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = Connector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(extractBookFromRes(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create connection to the DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (Connection connection = Connector.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                bookList.add(extractBookFromRes(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create connection to the DB", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = Connector.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(sql)) {

            prepareStatement.setString(1, book.getTitle());
            prepareStatement.setBigDecimal(2, book.getPrice());
            prepareStatement.setLong(3, book.getId());

            int affectedRows = prepareStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expended to inserted one row but it inserted 0 rows");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cant update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = Connector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Cant delete book from db where id = " + id, e);
        }
    }

    //THIS METHOD FOR FAST TEST AND CLEAN, don't use in a real application
    @Override
    public boolean dropDataBase() {
        String sql = "DROP DATABASE jdbc_intro_trokhymchuk";
        try (Connection connection = Connector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataProcessingException("Cant drop DB jdbc_intro_trokhymchu", e);
        }
    }

    private Book extractBookFromRes(ResultSet resultSet) {
        try {
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getObject("title", String.class);
            BigDecimal price = resultSet.getObject("price", BigDecimal.class);
            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract book from ResultSet", e);
        }
    }
}
