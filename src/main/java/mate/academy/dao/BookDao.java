package mate.academy.dao;

import mate.academy.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book create(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    boolean deleteId(Long id);
    Book update(Book book);
}
