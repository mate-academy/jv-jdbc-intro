package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.Book;
import java.util.List;

public interface BookDao {
    Book create(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(Long id);
}

