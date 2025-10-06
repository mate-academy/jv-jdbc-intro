package mate.academy.daobook;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface DaoBook {

    Book update(Book book);

    boolean deleteById(Long id);

    List<Book> findAll();

    Book create(Book book);

    Optional<Book> findById(Long id);
}
