package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public interface BookDao {

    Book create(Book book) throws DataProcessingException;

    Optional<Book> findById(Long id) throws DataProcessingException;

    List<Book> findAll() throws DataProcessingException;

    Book update(Book book) throws DataProcessingException;

    boolean deleteById(Long id) throws DataProcessingException;

}
