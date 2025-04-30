package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import model.Book;

public interface BookDao {
    void create(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(Long id);
}
