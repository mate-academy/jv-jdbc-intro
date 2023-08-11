package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {

    void create(Book book);

    List<Book> findAll();

    boolean deleteById(Long id);

    Book update(Book book);

    Optional<Book> findById(Long id);
}
