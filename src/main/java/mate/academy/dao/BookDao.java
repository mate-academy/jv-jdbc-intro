package mate.academy.dao;

import mate.academy.services.Book;

import java.util.Optional;

public interface BookDao {
    Book save(Book book);

    Book get(Long id);

    Optional<Book> findById(Long id);

    Book update(Book book);
}
