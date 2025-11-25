package mate.academy.dao;

import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.util.List;
import java.util.Optional;

@Dao
public interface BookDao {
    Book create(Book book);
    List<Book> findAll();
    Optional<Book> update(Book book);
    boolean deleteById(Long id);
}
