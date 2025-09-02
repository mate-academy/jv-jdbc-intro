package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

public interface BookDao {
    @Dao
    Optional<Book> findById(Long id);

    @Dao
    Book update(Book book);

    @Dao
    Book create(Book book);

    @Dao
    List<Book> findAll();

    @Dao
    boolean deleteById(Long id);
}
