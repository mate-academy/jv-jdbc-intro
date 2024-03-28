package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.models.Book;

public interface BookDao {
    //CRUD
    Book create(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book update(Book book);

    boolean deleteById(Long id);
}
