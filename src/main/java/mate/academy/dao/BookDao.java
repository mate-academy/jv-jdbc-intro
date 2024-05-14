package mate.academy.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import mate.academy.costomexeption.DataProcessingException;
import mate.academy.model.Book;

public interface BookDao {
    Book create(Book book) throws SQLException;

    List<Book> findAll() throws DataProcessingException;

    Optional<Book> findById(Long id) throws DataProcessingException;

    Book update(Book book) throws DataProcessingException, SQLException;

    boolean deleteById(Long id) throws DataProcessingException;
}
