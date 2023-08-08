package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {
    Book create(Book book);

    Optional<Book> get(Long id);

    List<Book> getAll();

    Book update(Book manufacturer);

    boolean delete(Long id);
}
