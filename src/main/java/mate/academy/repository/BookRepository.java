package mate.academy.repository;

import mate.academy.models.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    Optional<Book> updateById(Book book);
    void deleteById(Long id);
}
