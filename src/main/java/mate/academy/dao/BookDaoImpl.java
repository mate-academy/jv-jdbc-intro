package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.sql.*;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO books (title,price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, book.getTitle());
            statement.setInt(2,book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()){
                book.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't inset book: " + book, e);
        }
        return book;
    }

    @Override
    public Book get(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id",Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getObject("price", Integer.class));
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to the DB",e);
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Optional<Book> book = Optional.of(new Book());
                book.get().setId(resultSet.getObject("id",Long.class));
                book.get().setTitle(resultSet.getString("title"));
                book.get().setPrice(resultSet.getObject("price", Integer.class));
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to the DB",e);
        }
        return Optional.empty();
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,book.getTitle());
            statement.setInt(2,book.getPrice());
            statement.setLong(3,book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update book:" + book,e);
        }
        return book;
    }

    @Override
    public boolean delete(Book book) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1,book.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete book:" + book,e);
        }
    }
}
