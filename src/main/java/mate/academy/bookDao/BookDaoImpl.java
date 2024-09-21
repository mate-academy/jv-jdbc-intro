package mate.academy.bookDao;

import mate.academy.dbconnection.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM book")) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("tittle");
                int price = resultSet.getInt("price");
                Book book = new Book(id, title, price);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
