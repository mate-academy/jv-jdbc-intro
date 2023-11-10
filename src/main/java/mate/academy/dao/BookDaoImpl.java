package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.service.resultMapper.ResultSetMapperImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATION_EXCEPTION_MESSAGE = "Expected to insert 1 row, but inserted 0 rows";
    private static final String WRONG_ID_MESSAGE = "Cant find a book with id = ";
    private static ConnectionUtil connectionUtil;
    private static ResultSetMapperImpl mapper;

    public BookDaoImpl(ConnectionUtil connectionUtil, ResultSetMapperImpl mapper) {
        BookDaoImpl.connectionUtil = connectionUtil;
        BookDaoImpl.mapper = mapper;
    }

    @Override
    public Book create(Book book) {
        String title = book.getTitle();
        double price = book.getPrice().doubleValue();
        String query = "INSERT INTO books (title, price) VALUES(?, ?)";
        try (PreparedStatement statement = connectionUtil.makeConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, title);
            statement.setDouble(2, price);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(CREATION_EXCEPTION_MESSAGE);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong("id");
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CREATION_EXCEPTION_MESSAGE);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement statement = connectionUtil.makeConnection().prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapper.mapFromResultToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(WRONG_ID_MESSAGE + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> listOfBooks = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (PreparedStatement statement = connectionUtil.makeConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                listOfBooks.add(mapper.mapFromResultToBook(resultSet));
            }
            return listOfBooks;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try(PreparedStatement statement = connectionUtil.makeConnection().prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setDouble(2, book.getPrice().doubleValue());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Failed to update object with such id = " + book.getId());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error updating book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = connectionUtil.makeConnection().prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(WRONG_ID_MESSAGE + id, e);
        }
    }
}
