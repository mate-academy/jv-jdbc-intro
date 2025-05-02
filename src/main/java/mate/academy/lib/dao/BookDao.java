package mate.academy.lib.dao;

import java.util.List;
import java.util.Optional;
import model.Book;

public interface BookDao {

    Book create(Book book);

    Book update(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    boolean deleteById(Long id);
}
