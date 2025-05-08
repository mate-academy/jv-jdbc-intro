package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {
    // CRUD
    Book create(Book book);

    Optional<Book> findById(Long id);

    Book update(Book book);

    boolean delete(Long id);

    List<Book> findAll();
}
