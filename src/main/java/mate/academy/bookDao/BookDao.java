package mate.academy.bookDao;

import mate.academy.model.Book;

import java.util.List;
import java.util.Optional;

public interface bookDao {
    Book create(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(Long id);
}
