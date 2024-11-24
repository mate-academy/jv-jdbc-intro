package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {

    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(long id);

    Book update(Book book);

    void delete(Book book);

}
