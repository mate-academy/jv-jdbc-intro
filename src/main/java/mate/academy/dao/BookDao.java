package mate.academy.dao;

import java.util.List;
import mate.academy.model.Book;
import java.util.Optional;

public interface BookDao {
    Book create(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    Book update(Book book);
    boolean deleteById(Long id);
}
