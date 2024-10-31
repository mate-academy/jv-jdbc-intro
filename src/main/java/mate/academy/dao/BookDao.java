package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {

    void create(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    void update(Book book);

    void deleteById(Long id);
}
