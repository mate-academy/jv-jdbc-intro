package mate.academy.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.models.Book;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book updateById(Book book);

    boolean deleteById(Long id);
}
