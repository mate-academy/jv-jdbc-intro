package mate.academy.dao;

import mate.academy.services.Book;
import java.util.Optional;
import java.util.List;

public interface BookDao {
    Book create(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(Long id);
}
