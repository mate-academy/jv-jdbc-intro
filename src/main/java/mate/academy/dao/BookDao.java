package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {
    Book create(Book book);

    List<Book> findAll(Long id);

    Optional<Book> findById(Long id);

    Book update(Book book);

    boolean deleteById(Long id);
}
