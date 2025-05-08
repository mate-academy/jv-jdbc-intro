package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {

    Book create(Book book);

    List<Book> findAll();

    Book update(Book book);

    Optional<Book> findById(Long id);

    boolean deleteById(Long id);
}
