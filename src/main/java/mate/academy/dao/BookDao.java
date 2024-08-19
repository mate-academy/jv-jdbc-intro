package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.domain.Book;

public interface BookDao {

    Book create(Book book);

    Optional<Book> findById(int id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(int id);
}
