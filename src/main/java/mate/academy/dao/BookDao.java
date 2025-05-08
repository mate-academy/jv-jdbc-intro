package mate.academy.dao;

import java.util.List;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;

public interface BookDao {
    Book create(Book book);

    Object findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(Long id) throws DataProcessingException;
}
