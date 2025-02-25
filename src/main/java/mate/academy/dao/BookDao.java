package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {
    Book save(Book book);

    Book get(Long id);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean delete(Book book);
}
