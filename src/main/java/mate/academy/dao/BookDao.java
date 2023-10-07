package mate.academy.dao;

import mate.academy.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book update(Book book);

    boolean deleteById(Long id);
}
