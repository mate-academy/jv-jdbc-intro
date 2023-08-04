package mate.academy.dao;

import mate.academy.model.Book;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    Book create(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    Book update(Book book);
    boolean deleteById(Long id);
}
