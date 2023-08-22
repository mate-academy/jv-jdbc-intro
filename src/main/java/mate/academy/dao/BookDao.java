package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {
    List<Book> getAll();

    Book create(Book name);

    boolean delete(Long id);

    Book update(Book book);

    Optional<Book> get(Long id);
}
