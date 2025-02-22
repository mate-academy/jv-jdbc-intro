package mate.academy.dao;

import mate.academy.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book create(Book book);
    Optional<Book> findById(long id);
    List<Book> findAll();
    Book update(Book book);
    boolean deleteByID(Long id);
}
