package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {
    Book create(Book book);

    Book update(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    boolean delete(Book book);
}
