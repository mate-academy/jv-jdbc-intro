package mate.academy.dao;

import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.util.List;
import java.util.Optional;
@Dao
public interface BookDao {
    @Dao
    Book create(Book book);
    @Dao
    Optional<Book> findById(Long id);
    @Dao
    List<Book> findAll();
    @Dao
    Book update(Book book);
    @Dao
    boolean deleteById(Long id);
}
