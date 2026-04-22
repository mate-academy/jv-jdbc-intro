package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.entities.Book;

public interface BookDao {
    Book create(Book book);

    Optional<Book> findById(Long id);

    public List<Book> findAll();

    Book update(Book book);

    boolean deleteById(Long id);

}
