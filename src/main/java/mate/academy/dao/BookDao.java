package mate.academy.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.repository.BookDaoRepository;
import mate.academy.utility.Connector;

@Dao
public class BookDao implements BookDaoRepository {
    private final Connector connector;

    public BookDao() {
        connector = new Connector();
    }

    public Book create(Book book) {
        String sqlInsert = "INSERT INTO books (title, price) VALUES (?,?);";
        try (PreparedStatement preparedStatement =
                     Connector.getConnection().prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "There is a problem with connection to DB in create method!!", e);
        }
        return book;
    }

    public Optional<Book> findById(Long id) {
        String sqlFindById = "SELECT id, title, price FROM books WHERE id = ?;";
        Book book = new Book();
        try (PreparedStatement preparedStatement =
                     Connector.getConnection().prepareStatement(sqlFindById)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book.setId(resultSet.getLong(1));
                book.setTitle(resultSet.getString(2));
                book.setPrice(resultSet.getBigDecimal(3));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "There is a problem with connection to DB in findById method!!", e);
        }
        return Optional.of(book);
    }

    public List<Book> findAll() {
        String sqlGetAll = "SELECT * FROM books;";
        List<Book> books;
        try (PreparedStatement statement = Connector.getConnection().prepareStatement(sqlGetAll)) {
            ResultSet resultSet = statement.executeQuery();
            books = getFromResultSetToBookList(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "There is a problem with connection to DB in findAll method!!", e);
        }
        return Optional.of(books).get();
    }

    public Book update(Book book) {
        String sqlUpdate = "UPDATE books SET title = ?, price = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement =
                     Connector.getConnection().prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "There is a problem with connection to DB in update method!!", e);
        }
        return book;
    }

    public boolean deleteById(Long id) {
        String sqlDelete = "DELETE FROM books WHERE id = ?;";
        int executionResult;
        try (PreparedStatement preparedStatement =
                     Connector.getConnection().prepareStatement(sqlDelete)) {
            preparedStatement.setLong(1, id);
            executionResult = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "There is a problem with connection to DB in delete method!!", e);
        }
        return executionResult > 0;
    }

    private List<Book> getFromResultSetToBookList(ResultSet resultSet) {
        List<Book> list = new ArrayList<>();
        try (resultSet) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong(1));
                book.setTitle(resultSet.getString(2));
                book.setPrice(resultSet.getBigDecimal(3));
                list.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "There is a problem with get data "
                            + "from DB in getFromResultSetToBookList method!!", e);
        }
        return list;
    }
}
