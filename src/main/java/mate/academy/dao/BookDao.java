package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {
    Optional<Book> findById(Long id);

    Book update(Book book);

    Book create(Book book);

    List<Book> findAll();

    boolean deleteById(Long id);
}
