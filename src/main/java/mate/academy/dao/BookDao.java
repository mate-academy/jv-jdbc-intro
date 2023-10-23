package mate.academy.dao;

import java.util.Optional;
import java.util.List;
import mate.academy.model.Book;

public interface BookDao {
    Book create(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book update(Book book);

    boolean delete(Book book);
}
